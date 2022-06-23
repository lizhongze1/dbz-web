package com.ruoyi.system.connect;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.utils.Exit;
import org.apache.kafka.common.utils.Time;
import org.apache.kafka.connect.runtime.Connect;
import org.apache.kafka.connect.runtime.Worker;
import org.apache.kafka.connect.runtime.WorkerConfigTransformer;
import org.apache.kafka.connect.runtime.WorkerInfo;
import org.apache.kafka.connect.runtime.distributed.DistributedConfig;
import org.apache.kafka.connect.runtime.distributed.DistributedHerder;
import org.apache.kafka.connect.runtime.isolation.Plugins;
import org.apache.kafka.connect.runtime.rest.RestServer;
import org.apache.kafka.connect.storage.*;
import org.apache.kafka.connect.util.ConnectUtils;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.HashMap;
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
public class ConnectApplication implements ApplicationListener<ApplicationStartedEvent> {


    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        try {
            Time time = Time.SYSTEM;
            log.info("Kafka Connect distributed worker initializing ...");
            long initStart = time.hiResClockMs();
            WorkerInfo initInfo = new WorkerInfo();
            initInfo.logAll();

    /*          String workerPropsFile = "";
          Map<String, String> workerProps = !workerPropsFile.isEmpty() ?
                    Utils.propsToStringMap(Utils.loadProps(workerPropsFile)) : Collections.<String, String>emptyMap()*/
            ;
            Map<String, String> workerProps = new HashMap<>();
            workerProps.put("plugin.path", "D:\\javaSource\\RuoYi-Vue\\sql");
            workerProps.put("key.converter", "io.confluent.connect.avro.AvroConverter");
            workerProps.put("value.converter", "io.confluent.connect.avro.AvroConverter");

            workerProps.put("group.id", "connect-cluster");
            workerProps.put("offset.storage.topic", "connect-offsets");


            workerProps.put("config.storage.topic", "connect-configs");
            workerProps.put("status.storage.topic", "connect-status");
            workerProps.put("bootstrap.servers", "localhost:9092");
            workerProps.put("offset.storage.replication.factor", "1");
            workerProps.put("config.storage.replication.factor", "1");
            workerProps.put("status.storage.replication.factor", "1");


            log.info("Scanning for plugin classes. This might take a moment ...");
/*            bootstrap.servers=localhost:9092
            group.id=connect-cluster
            key.converter.schemas.enable=true
            value.converter.schemas.enable=true
            offset.storage.topic=connect-offsets
            offset.storage.replication.factor=1
#offset.storage.partitions=25
            config.storage.topic=connect-configs
            config.storage.replication.factor=1
            status.storage.topic=connect-status
            status.storage.replication.factor=1
#status.storage.partitions=5
            offset.flush.interval.ms=10000*/


            Plugins plugins = new Plugins(workerProps);
            plugins.compareAndSwapWithDelegatingLoader();
            DistributedConfig config = new DistributedConfig(workerProps);

            String kafkaClusterId = ConnectUtils.lookupKafkaClusterId(config);
            log.debug("Kafka cluster ID: {}", kafkaClusterId);

            RestServer rest = new RestServer(config);
            URI advertisedUrl = rest.advertisedUrl();
            String workerId = advertisedUrl.getHost() + ":" + advertisedUrl.getPort();

            KafkaOffsetBackingStore offsetBackingStore = new KafkaOffsetBackingStore();
            offsetBackingStore.configure(config);

            Worker worker = new Worker(workerId, time, plugins, config, offsetBackingStore);
            WorkerConfigTransformer configTransformer = worker.configTransformer();

            Converter internalValueConverter = worker.getInternalValueConverter();
            StatusBackingStore statusBackingStore = new KafkaStatusBackingStore(time, internalValueConverter);
            statusBackingStore.configure(config);

            ConfigBackingStore configBackingStore = new KafkaConfigBackingStore(
                    internalValueConverter,
                    config,
                    configTransformer);

            DistributedHerder herder = new DistributedHerder(config, time, worker,
                    kafkaClusterId, statusBackingStore, configBackingStore,
                    advertisedUrl.toString());
            final Connect connect = new Connect(herder, rest);
            log.info("Kafka Connect distributed worker initialization took {}ms", time.hiResClockMs() - initStart);
            try {
                connect.start();
            } catch (Exception e) {
                log.error("Failed to start Connect", e);
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
