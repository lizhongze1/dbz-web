package com.ruoyi.system.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.utils.Time;
import org.apache.kafka.connect.runtime.Worker;
import org.apache.kafka.connect.runtime.WorkerConfigTransformer;
import org.apache.kafka.connect.runtime.WorkerInfo;
import org.apache.kafka.connect.runtime.distributed.DistributedConfig;
import org.apache.kafka.connect.runtime.distributed.DistributedHerder;
import org.apache.kafka.connect.runtime.isolation.Plugins;
import org.apache.kafka.connect.runtime.rest.RestServer;
import org.apache.kafka.connect.runtime.rest.entities.ConfigInfos;
import org.apache.kafka.connect.storage.*;
import org.apache.kafka.connect.util.ConnectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * 　  * @className: ConnectConfig
 * 　　* @description:TODO
 * 　　* @param:
 * 　　* @return:
 * 　　* @throws:
 * 　　* @email lzz215855518@gmail.com
 * 　　* @author: lizz
 * 　　* @date: 2022/06/23 13:24
 */
//@Configuration
@Slf4j
public class ClusterConnectConfig {
    @Bean
    @DependsOn("plugins")
    public WorkerInfo workerInfo() {
        return new WorkerInfo();
    }


    @Bean
    public Plugins plugins(Map<String, String> workerProps) {
        Plugins plugins = new Plugins(workerProps);
        plugins.compareAndSwapWithDelegatingLoader();
        return plugins;
    }

    @Bean
    @DependsOn("plugins")
    public DistributedConfig distributedConfig(Map<String, String> workerProps) {
        return new DistributedConfig(workerProps);
    }

    @Bean
    @DependsOn("plugins")
    public RestServer restServer(DistributedConfig distributedConfig) {
        return new RestServer(distributedConfig);
    }

    @Bean
    public Map<String, String> workerProps() {
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
        return workerProps;
    }

    @Bean
    public KafkaOffsetBackingStore kafkaOffsetBackingStore(DistributedConfig distributedConfig) {
        KafkaOffsetBackingStore kafkaOffsetBackingStore = new KafkaOffsetBackingStore();
        kafkaOffsetBackingStore.configure(distributedConfig);
        return kafkaOffsetBackingStore;
    }

    @Bean
    public Worker Worker(RestServer restServer, Plugins plugins, DistributedConfig config, KafkaOffsetBackingStore offsetBackingStore) {
        Time time = Time.SYSTEM;
        log.info("Kafka Connect distributed worker initializing ...");
        URI advertisedUrl = restServer.advertisedUrl();
        String workerId = advertisedUrl.getHost() + ":" + advertisedUrl.getPort();
        Worker worker = new Worker(workerId, time, plugins, config, offsetBackingStore);
        return worker;
    }

    @Bean
    public KafkaStatusBackingStore KafkaStatusBackingStore(Worker worker, DistributedConfig config) {
        Time time = Time.SYSTEM;
        Converter internalValueConverter = worker.getInternalValueConverter();
        KafkaStatusBackingStore kafkaStatusBackingStore = new KafkaStatusBackingStore(time, internalValueConverter);
        kafkaStatusBackingStore.configure(config);
        return kafkaStatusBackingStore;
    }


    @Bean
    public ConfigBackingStore configBackingStore(Worker worker, DistributedConfig config) {
        Converter internalValueConverter = worker.getInternalValueConverter();
        WorkerConfigTransformer configTransformer = worker.configTransformer();
        ConfigBackingStore configBackingStore = new KafkaConfigBackingStore(
                internalValueConverter,
                config,
                configTransformer);
        return configBackingStore;
    }

    @Bean
    public DistributedHerder herder(DistributedConfig config, Worker worker, StatusBackingStore statusBackingStore, ConfigBackingStore configBackingStore, RestServer rest) {
        Time time = Time.SYSTEM;
        URI advertisedUrl = rest.advertisedUrl();
        String kafkaClusterId = ConnectUtils.lookupKafkaClusterId(config);
        DistributedHerder herder = new DistributedHerder(config, time, worker,
                kafkaClusterId, statusBackingStore, configBackingStore,
                advertisedUrl.toString());
        return herder;
    }


    @Bean
    @Order(999)
    public ConfigInfos validateConfigs(Map<String, String> workerProps, DistributedHerder herder) throws Throwable {

        return herder.validateConnectorConfig(workerProps);
    }


}
