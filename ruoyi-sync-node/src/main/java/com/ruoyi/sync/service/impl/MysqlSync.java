package com.ruoyi.sync.service.impl;

import com.ruoyi.sync.service.DbzSync;
import io.debezium.embedded.Connect;
import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.RecordChangeEvent;
import io.debezium.engine.format.ChangeEventFormat;
import io.debezium.engine.format.Json;
import org.apache.kafka.connect.source.SourceRecord;

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
public class MysqlSync extends AbstractSync implements DbzSync {
    final Properties props = new Properties();

    @Override
    public boolean start() throws IOException {
        final Properties props = new Properties();

        props.setProperty("name", "engine");
        props.setProperty("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore");
        props.setProperty("offset.storage.file.filename", "C:\\Users\\21585\\Pictures\\offsets.dat");
        props.setProperty("offset.flush.interval.ms", "60000");
        /* begin connector properties */
        props.setProperty("database.hostname", "localhost");
        props.setProperty("database.port", "3306");
        props.setProperty("database.user", "root");
        props.setProperty("database.password", "root");
        props.setProperty("database.server.id", "85744");
        props.setProperty("database.server.name", "my-app-connector");
        props.setProperty("database.history",
                "io.debezium.relational.history.FileDatabaseHistory");
        props.setProperty("database.history.file.filename",
                "C:\\Users\\21585\\Pictures\\dbhistory.dat");

                props.setProperty("connector.class", "io.debezium.connector.mysql.MySqlConnector");
        props.setProperty("table.whitlelist", "employees,222");
        props.setProperty("database.whitelist", "aba");

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
