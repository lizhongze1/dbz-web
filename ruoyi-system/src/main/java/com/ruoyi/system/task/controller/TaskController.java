package com.ruoyi.system.task.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.ruoyi.common.core.controller.BaseController;
import org.apache.kafka.connect.runtime.ConnectorConfig;
import org.apache.kafka.connect.runtime.Herder;
import org.apache.kafka.connect.runtime.WorkerConfig;
import org.apache.kafka.connect.runtime.distributed.RebalanceNeededException;
import org.apache.kafka.connect.runtime.distributed.RequestTargetException;
import org.apache.kafka.connect.runtime.rest.RestClient;
import org.apache.kafka.connect.runtime.rest.entities.ConnectorInfo;
import org.apache.kafka.connect.runtime.rest.entities.ConnectorStateInfo;
import org.apache.kafka.connect.runtime.rest.entities.CreateConnectorRequest;
import org.apache.kafka.connect.runtime.rest.entities.TaskInfo;
import org.apache.kafka.connect.runtime.rest.errors.ConnectRestException;
import org.apache.kafka.connect.runtime.rest.resources.ConnectorsResource;
import org.apache.kafka.connect.util.ConnectorTaskId;
import org.apache.kafka.connect.util.FutureCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 数据源Controller
 *
 * @author lizz
 * @date 2020-11-17
 */
@RestController
@RequestMapping("/connectors")
public class TaskController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(ConnectorsResource.class);

    // TODO: This should not be so long. However, due to potentially long rebalances that may have to wait a full
    // session timeout to complete, during which we cannot serve some requests. Ideally we could reduce this, but
    // we need to consider all possible scenarios this could fail. It might be ok to fail with a timeout in rare cases,
    // but currently a worker simply leaving the group can take this long as well.
    private static final long REQUEST_TIMEOUT_MS = 90 * 1000;
    @Autowired
    private Herder herder;
    @Autowired
    private WorkerConfig config;
    @javax.ws.rs.core.Context
    private ServletContext context;

    public TaskController(Herder herder, WorkerConfig config) {
        this.herder = herder;
        this.config = config;
    }


    @GetMapping
    public Collection<String> listConnectors(final @QueryParam("forward") Boolean forward) throws Throwable {
        FutureCallback<Collection<String>> cb = new FutureCallback<>();
        herder.connectors(cb);
        return completeOrForwardRequest(cb, "/connectors", "GET", null, new TypeReference<Collection<String>>() {
        }, forward);
    }

    @PostMapping
    public Response createConnector(@QueryParam("forward") Boolean forward,
                                    @RequestBody CreateConnectorRequest createRequest) throws Throwable {
        // Trim leading and trailing whitespaces from the connector name, replace null with empty string
        // if no name element present to keep validation within validator (NonEmptyStringWithoutControlChars
        // allows null values)
        String name = createRequest.name() == null ? "" : createRequest.name().trim();

        Map<String, String> configs = createRequest.config();
        checkAndPutConnectorConfigName(name, configs);

        FutureCallback<Herder.Created<ConnectorInfo>> cb = new FutureCallback<>();
        herder.putConnectorConfig(name, configs, false, cb);
        Herder.Created<ConnectorInfo> info = completeOrForwardRequest(cb, "/connectors", "POST", createRequest,
                new TypeReference<ConnectorInfo>() {
                }, new TaskController.CreatedConnectorInfoTranslator(), forward);

        URI location = UriBuilder.fromUri("/connectors").path(name).build();
        return Response.created(location).entity(info.result()).build();
    }

    @GetMapping("/{connector}")
    public ConnectorInfo getConnector(final @PathVariable("connector") String connector,
                                      final @QueryParam("forward") Boolean forward) throws Throwable {
        FutureCallback<ConnectorInfo> cb = new FutureCallback<>();
        herder.connectorInfo(connector, cb);
        return completeOrForwardRequest(cb, "/connectors/" + connector, "GET", null, forward);
    }

    @GetMapping("/{connector}/config")
    public Map<String, String> getConnectorConfig(final @PathVariable("connector") String connector,
                                                  final @QueryParam("forward") Boolean forward) throws Throwable {
        FutureCallback<Map<String, String>> cb = new FutureCallback<>();
        herder.connectorConfig(connector, cb);
        return completeOrForwardRequest(cb, "/connectors/" + connector + "/config", "GET", null, forward);
    }

    @GetMapping("/{connector}/status")
    public ConnectorStateInfo getConnectorStatus(final @PathVariable("connector") String connector) throws Throwable {
        return herder.connectorStatus(connector);
    }


    @PutMapping("/{connector}/config")
    public Response putConnectorConfig(final @PathVariable("connector") String connector,
                                       final @QueryParam("forward") Boolean forward,
                                       final Map<String, String> connectorConfig) throws Throwable {
        FutureCallback<Herder.Created<ConnectorInfo>> cb = new FutureCallback<>();
        checkAndPutConnectorConfigName(connector, connectorConfig);

        herder.putConnectorConfig(connector, connectorConfig, true, cb);
        Herder.Created<ConnectorInfo> createdInfo = completeOrForwardRequest(cb, "/connectors/" + connector + "/config",
                "PUT", connectorConfig, new TypeReference<ConnectorInfo>() {
                }, new TaskController.CreatedConnectorInfoTranslator(), forward);
        Response.ResponseBuilder response;
        if (createdInfo.created()) {
            URI location = UriBuilder.fromUri("/connectors").path(connector).build();
            response = Response.created(location);
        } else {
            response = Response.ok();
        }
        return response.entity(createdInfo.result()).build();
    }


    @PostMapping("/{connector}/restart")
    public void restartConnector(final @PathVariable("connector") String connector,
                                 final @QueryParam("forward") Boolean forward) throws Throwable {
        FutureCallback<Void> cb = new FutureCallback<>();
        herder.restartConnector(connector, cb);
        completeOrForwardRequest(cb, "/connectors/" + connector + "/restart", "POST", null, forward);
    }


    @PutMapping("/{connector}/pause")
    public Response pauseConnector(@PathVariable("connector") String connector) {
        herder.pauseConnector(connector);
        return Response.accepted().build();
    }

    @PutMapping("/{connector}/resume")
    public Response resumeConnector(@PathVariable("connector") String connector) {
        herder.resumeConnector(connector);
        return Response.accepted().build();
    }

    @GetMapping("/{connector}/tasks")
    public List<TaskInfo> getTaskConfigs(final @PathVariable("connector") String connector,
                                         final @QueryParam("forward") Boolean forward) throws Throwable {
        FutureCallback<List<TaskInfo>> cb = new FutureCallback<>();
        herder.taskConfigs(connector, cb);
        return completeOrForwardRequest(cb, "/connectors/" + connector + "/tasks", "GET", null, new TypeReference<List<TaskInfo>>() {
        }, forward);
    }


    @PostMapping("/{connector}/tasks")
    public void putTaskConfigs(final @PathVariable("connector") String connector,
                               final @QueryParam("forward") Boolean forward,
                               final List<Map<String, String>> taskConfigs) throws Throwable {
        FutureCallback<Void> cb = new FutureCallback<>();
        herder.putTaskConfigs(connector, taskConfigs, cb);
        completeOrForwardRequest(cb, "/connectors/" + connector + "/tasks", "POST", taskConfigs, forward);
    }


    @GetMapping("/{connector}/tasks/{task}/status")
    public ConnectorStateInfo.TaskState getTaskStatus(final @PathVariable("connector") String connector,
                                                      final @PathVariable("task") Integer task) throws Throwable {
        return herder.taskStatus(new ConnectorTaskId(connector, task));
    }


    @PostMapping("/{connector}/tasks/{task}/restart")
    public void restartTask(final @PathVariable("connector") String connector,
                            final @PathVariable("task") Integer task,
                            final @QueryParam("forward") Boolean forward) throws Throwable {
        FutureCallback<Void> cb = new FutureCallback<>();
        ConnectorTaskId taskId = new ConnectorTaskId(connector, task);
        herder.restartTask(taskId, cb);
        completeOrForwardRequest(cb, "/connectors/" + connector + "/tasks/" + task + "/restart", "POST", null, forward);
    }

    @DeleteMapping("{connector}")
    public void destroyConnector(final @PathVariable("connector") String connector,
                                 final @QueryParam("forward") Boolean forward) throws Throwable {
        FutureCallback<Herder.Created<ConnectorInfo>> cb = new FutureCallback<>();
        herder.deleteConnectorConfig(connector, cb);
        completeOrForwardRequest(cb, "/connectors/" + connector, "DELETE", null, forward);
    }

    // Check whether the connector name from the url matches the one (if there is one) provided in the connectorconfig
    // object. Throw BadRequestException on mismatch, otherwise put connectorname in config
    private void checkAndPutConnectorConfigName(String connectorName, Map<String, String> connectorConfig) {
        String includedName = connectorConfig.get(ConnectorConfig.NAME_CONFIG);
        if (includedName != null) {
            if (!includedName.equals(connectorName))
                throw new BadRequestException("Connector name configuration (" + includedName + ") doesn't match connector name in the URL (" + connectorName + ")");
        } else {
            connectorConfig.put(ConnectorConfig.NAME_CONFIG, connectorName);
        }
    }

    // Wait for a FutureCallback to complete. If it succeeds, return the parsed response. If it fails, try to forward the
    // request to the leader.
    private <T, U> T completeOrForwardRequest(FutureCallback<T> cb,
                                              String path,
                                              String method,
                                              Object body,
                                              TypeReference<U> resultType,
                                              TaskController.Translator<T, U> translator,
                                              Boolean forward) throws Throwable {
        try {
            return cb.get(REQUEST_TIMEOUT_MS, TimeUnit.MILLISECONDS);
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();

            if (cause instanceof RequestTargetException) {
                if (forward == null || forward) {
                    // the only time we allow recursive forwarding is when no forward flag has
                    // been set, which should only be seen by the first worker to handle a user request.
                    // this gives two total hops to resolve the request before giving up.
                    boolean recursiveForward = forward == null;
                    RequestTargetException targetException = (RequestTargetException) cause;
                    String forwardUrl = UriBuilder.fromUri(targetException.forwardUrl())
                            .path(path)
                            .queryParam("forward", recursiveForward)
                            .build()
                            .toString();
                    log.debug("Forwarding request {} {} {}", forwardUrl, method, body);
                    return translator.translate(RestClient.httpRequest(forwardUrl, method, body, resultType, config));
                } else {
                    // we should find the right target for the query within two hops, so if
                    // we don't, it probably means that a rebalance has taken place.
                    throw new ConnectRestException(Response.Status.CONFLICT.getStatusCode(),
                            "Cannot complete request because of a conflicting operation (e.g. worker rebalance)");
                }
            } else if (cause instanceof RebalanceNeededException) {
                throw new ConnectRestException(Response.Status.CONFLICT.getStatusCode(),
                        "Cannot complete request momentarily due to stale configuration (typically caused by a concurrent config change)");
            }

            throw cause;
        } catch (TimeoutException e) {
            // This timeout is for the operation itself. None of the timeout error codes are relevant, so internal server
            // error is the best option
            throw new ConnectRestException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "Request timed out");
        } catch (InterruptedException e) {
            throw new ConnectRestException(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "Request interrupted");
        }
    }

    private <T> T completeOrForwardRequest(FutureCallback<T> cb, String path, String method, Object body,
                                           TypeReference<T> resultType, Boolean forward) throws Throwable {
        return completeOrForwardRequest(cb, path, method, body, resultType, new TaskController.IdentityTranslator<T>(), forward);
    }

    private <T> T completeOrForwardRequest(FutureCallback<T> cb, String path, String method,
                                           Object body, Boolean forward) throws Throwable {
        return completeOrForwardRequest(cb, path, method, body, null, new TaskController.IdentityTranslator<T>(), forward);
    }

    private interface Translator<T, U> {
        T translate(RestClient.HttpResponse<U> response);
    }

    private static class IdentityTranslator<T> implements TaskController.Translator<T, T> {
        @Override
        public T translate(RestClient.HttpResponse<T> response) {
            return response.body();
        }
    }

    private static class CreatedConnectorInfoTranslator implements TaskController.Translator<Herder.Created<ConnectorInfo>, ConnectorInfo> {
        @Override
        public Herder.Created<ConnectorInfo> translate(RestClient.HttpResponse<ConnectorInfo> response) {
            boolean created = response.status() == 201;
            return new Herder.Created<>(created, response.body());
        }
    }

}
