package com.ruoyi.sync.service.impl;

import com.ruoyi.sync.annotation.SyncDataSourceHandler;
import com.ruoyi.sync.enums.SyncDataSource;
import com.ruoyi.sync.service.DbzSync;
import io.debezium.embedded.Connect;
import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.RecordChangeEvent;
import io.debezium.engine.format.ChangeEventFormat;
import io.debezium.engine.format.Json;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.connect.source.SourceRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 　  * @className: MysqlSync
 * 　　* @description:TODO
 * 　　* @param:
 * 　　* @return:
 * 　　* @throws:
 * 　　* @email lzz215855518@gmail.com
 * 　　* @author: lizz
 * 　　* @date: 2020/12/05 14:03
 */
@SyncDataSourceHandler(value =SyncDataSource.MYSQL)
@Component
@Slf4j
public class MysqlSync extends AbstractSync implements DbzSync {
    @Value("${sync.file}")
    private String filePath;
    final Properties props = new Properties();

    @Override
    public boolean start(com.ruoyi.system.domain.SyncInstanceConfig syncDataSource) throws IOException {
        final Properties props = new Properties();
        com.ruoyi.system.domain.SyncDataSource sourceDs=syncDataSource.getSourceDs();
        String url= syncDataSource.getSourceDs().getUrl();
        String host=url.substring(url.indexOf("//")+2,url.lastIndexOf(":"));
        String port=url.substring(url.lastIndexOf(":")+1,url.lastIndexOf("/"));
        String database=url.substring(url.lastIndexOf("/")+1,url.length());
        log.info("url={},host={},port={},database={}",url,host,port,database);
        props.setProperty("name", syncDataSource.getName());
        props.setProperty("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore");
        props.setProperty("offset.storage.file.filename", filePath+"\\"+"offset\\"+syncDataSource.getId()+"\\storage.dat");
        props.setProperty("offset.flush.interval.ms", "60000");
        /* begin connector properties */
        props.setProperty("database.hostname", host);
        props.setProperty("database.port", port);
        props.setProperty("database.user", sourceDs.getUsername());
        props.setProperty("database.password", sourceDs.getPassword());
        props.setProperty("database.server.id", syncDataSource.getId()+"899");

       props.setProperty("database.server.name", syncDataSource.getName());
        props.setProperty("database.history",
                "io.debezium.relational.history.FileDatabaseHistory");
        props.setProperty("database.history.file.filename",
                filePath+"\\"+"offset\\"+syncDataSource.getId()+"\\history.dat");

                props.setProperty("connector.class", "io.debezium.connector.mysql.MySqlConnector");
        props.setProperty("database.whitelist", database);

        // Create the engine with this configuration ...
/*        DebeziumEngine<RecordChangeEvent<SourceRecord>> engine = DebeziumEngine.create(ChangeEventFormat.of(Connect.class))
                .using(props)
                .notifying(new MyChangeConsumer())
                .build();*/
        final DebeziumEngine<ChangeEvent<String, String>> engine = (DebeziumEngine<ChangeEvent<String, String>>) DebeziumEngine.create(Json.class)
                .using(props).notifying((records, committer) -> {

                    for (ChangeEvent<String, String> r : records) {
                        System.out.println("Key = '" + r.key() + "' value = '" + r.value() + "'");
                        committer.markProcessed(r);
                    }}).build();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(engine);
    return true;
    }

    class MyChangeConsumer implements DebeziumEngine.ChangeConsumer<RecordChangeEvent<SourceRecord>> {
        @Override
        public void handleBatch(List<RecordChangeEvent<SourceRecord>> records, DebeziumEngine.RecordCommitter<RecordChangeEvent<SourceRecord>> committer) throws InterruptedException {
            System.out.println("-----------------"+records+committer+"-------------------");
        }
    }
}
