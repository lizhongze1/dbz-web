package com.ruoyi.system.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.utils.Time;
import org.apache.kafka.connect.runtime.Worker;
import org.apache.kafka.connect.runtime.WorkerInfo;
import org.apache.kafka.connect.runtime.isolation.Plugins;
import org.apache.kafka.connect.runtime.rest.RestServer;
import org.apache.kafka.connect.runtime.rest.entities.ConfigInfos;
import org.apache.kafka.connect.runtime.standalone.StandaloneConfig;
import org.apache.kafka.connect.runtime.standalone.StandaloneHerder;
import org.apache.kafka.connect.storage.FileOffsetBackingStore;
import org.apache.kafka.connect.util.ConnectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@Configuration
@Slf4j
public class StandaloneConnectConfig {
    @Bean
    @DependsOn("plugins")
    public WorkerInfo workerInfo() {
        return new WorkerInfo();
    }


    @Bean
    @Order(-999)
    public Plugins plugins(Map<String, String> workerProps) {
        Plugins plugins = new Plugins(workerProps);
        plugins.compareAndSwapWithDelegatingLoader();
        return plugins;
    }

    /*    @Bean
        public DistributedConfig distributedConfig(Map<String, String> workerProps) {
            return new DistributedConfig(workerProps);
        }*/
    @Bean
    @DependsOn("plugins")
    StandaloneConfig standaloneConfig(Map<String, String> workerProps) {
        return new StandaloneConfig(workerProps);
    }

    @Bean
    @DependsOn("plugins")
    public RestServer restServer(StandaloneConfig standaloneConfig) {
        return new RestServer(standaloneConfig);
    }

    @Bean
    public Map<String, String> workerProps() {
        Map<String, String> workerProps = new HashMap<>();
        workerProps.put("plugin.path", "D:\\javaSource\\RuoYi-Vue\\sql");
        workerProps.put("key.converter", "org.apache.kafka.connect.json.JsonConverter");
        workerProps.put("value.converter", "org.apache.kafka.connect.json.JsonConverter");

        workerProps.put("connector.class", "io.debezium.connector.mysql.MySqlConnector");
        workerProps.put("name", "izz");
        workerProps.put("database.history.kafka.topic", "izz");

        workerProps.put("tasks.max", "100");
        workerProps.put("database.hostname", "localhost");
        workerProps.put("database.user", "root");
        workerProps.put("database.password", "root");
        workerProps.put("database.server.name", "localhost");
        workerProps.put("database.history.kafka.bootstrap.servers", "localhost:9092");
        workerProps.put("bootstrap.servers", "localhost:9092");

        workerProps.put("offset.storage.file.filename", "/tmp/connect.offsets");

        return workerProps;
    }

    @Bean
    @DependsOn("plugins")
    public FileOffsetBackingStore fileOffsetBackingStore() {
        return new FileOffsetBackingStore();
    }

    @Bean
    @DependsOn("plugins")
    public Worker Worker(RestServer restServer, Plugins plugins, StandaloneConfig config, FileOffsetBackingStore fileOffsetBackingStore) {
        Time time = Time.SYSTEM;
        log.info("Kafka Connect distributed worker initializing ...");
        URI advertisedUrl = restServer.advertisedUrl();
        String workerId = advertisedUrl.getHost() + ":" + advertisedUrl.getPort();
        Worker worker = new Worker(workerId, time, plugins, config, fileOffsetBackingStore);
        return worker;
    }

/*
    @Bean
    public KafkaStatusBackingStore KafkaStatusBackingStore(Worker worker, DistributedConfig config) {
        Time time = Time.SYSTEM;
        Converter internalValueConverter = worker.getInternalValueConverter();
        KafkaStatusBackingStore kafkaStatusBackingStore = new KafkaStatusBackingStore(time, internalValueConverter);
        kafkaStatusBackingStore.configure(config);
        return kafkaStatusBackingStore;
    }
*/


/*
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
*/

    /*    @Bean
        public DistributedHerder herder(DistributedConfig config, Worker worker, StatusBackingStore statusBackingStore, ConfigBackingStore configBackingStore, RestServer rest) {
            Time time = Time.SYSTEM;
            URI advertisedUrl = rest.advertisedUrl();
            String kafkaClusterId = ConnectUtils.lookupKafkaClusterId(config);
            DistributedHerder herder = new DistributedHerder(config, time, worker,
                    kafkaClusterId, statusBackingStore, configBackingStore,
                    advertisedUrl.toString());
            return herder;
        }*/
    @Bean
    @DependsOn("plugins")
    public StandaloneHerder herder(StandaloneConfig config, Worker worker, RestServer rest) {
        Time time = Time.SYSTEM;
        URI advertisedUrl = rest.advertisedUrl();
        String kafkaClusterId = ConnectUtils.lookupKafkaClusterId(config);
        return new StandaloneHerder(worker, kafkaClusterId);

    }

    @Bean
    @Order(999)
    public ConfigInfos validateConfigs(Map<String, String> workerProps, StandaloneHerder herder) throws Throwable {

        return herder.validateConnectorConfig(workerProps);
    }
}
