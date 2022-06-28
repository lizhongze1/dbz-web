package com.ruoyi.system.connect.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.utils.Exit;
import org.apache.kafka.common.utils.Time;
import org.apache.kafka.connect.runtime.Connect;
import org.apache.kafka.connect.runtime.ConnectorConfig;
import org.apache.kafka.connect.runtime.Herder;
import org.apache.kafka.connect.runtime.WorkerInfo;
import org.apache.kafka.connect.runtime.rest.RestServer;
import org.apache.kafka.connect.runtime.rest.entities.ConnectorInfo;
import org.apache.kafka.connect.runtime.standalone.StandaloneConfig;
import org.apache.kafka.connect.util.Callback;
import org.apache.kafka.connect.util.ConnectUtils;
import org.apache.kafka.connect.util.FutureCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.Map;

/**
 * 　  * @className: ConnectApplication
 * 　　* @description:TODO
 * 　　* @param:
 * 　　* @return:
 * 　　* @throws:
 * 　　* @email lzz215855518@gmail.com
 * 　　* @author: lizz
 * 　　* @date: 2022/06/22 17:48
 */
@Slf4j
@Component
public class StandaloneConnectApplication implements ApplicationListener<ApplicationStartedEvent> {
    @Autowired
    private WorkerInfo initInfo;
    ;
    @Autowired
    private StandaloneConfig config;

    @Autowired
    private RestServer rest;

    @Autowired
    private Herder herder;
    @Autowired
    Map<String, String> workerProps;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {


        try {
            Time time = Time.SYSTEM;
            log.info("Kafka Connect standalone worker initializing ...");
            long initStart = time.hiResClockMs();
            initInfo.logAll();

            log.info("Scanning for plugin classes. This might take a moment ...");


            String kafkaClusterId = ConnectUtils.lookupKafkaClusterId(config);
            log.debug("Kafka cluster ID: {}", kafkaClusterId);


            URI advertisedUrl = rest.advertisedUrl();
            String workerId = advertisedUrl.getHost() + ":" + advertisedUrl.getPort();

            final Connect connect = new Connect(herder, rest);
            log.info("Kafka Connect standalone worker initialization took {}ms", time.hiResClockMs() - initStart);

            try {
                connect.start();

                Map<String, String> connectorProps = workerProps;
                FutureCallback<Herder.Created<ConnectorInfo>> cb = new FutureCallback<>(new Callback<Herder.Created<ConnectorInfo>>() {
                    @Override
                    public void onCompletion(Throwable error, Herder.Created<ConnectorInfo> info) {
                        if (error != null)
                            log.error("Failed to create job for {}", connectorProps);
                        else
                            log.info("Created connector {}", info.result().name());
                    }
                });
                herder.putConnectorConfig(
                        connectorProps.get(ConnectorConfig.NAME_CONFIG),
                        connectorProps, false, cb);
                cb.get();

            } catch (Throwable t) {
                log.error("Stopping after connector error", t);
                connect.stop();
                Exit.exit(3);
            }

            // Shutdown will be triggered by Ctrl-C or via HTTP shutdown request
            connect.awaitStop();

        } catch (Throwable t) {
            log.error("Stopping due to error", t);
            Exit.exit(2);
        }
    }


}
