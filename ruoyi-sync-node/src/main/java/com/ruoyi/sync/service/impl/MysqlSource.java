package com.ruoyi.sync.service.impl;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.sync.annotation.SyncDataSourceHandler;
import com.ruoyi.sync.embedded.AbstractDebeziumSqlProvider;
import com.ruoyi.sync.embedded.DebeziumRecordUtils;
import com.ruoyi.sync.embedded.DebeziumSqlProviderFactory;
import com.ruoyi.sync.enums.SyncDataSource;
import com.ruoyi.sync.service.DbzSource;
import io.debezium.data.Envelope;
import io.debezium.embedded.EmbeddedEngine;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.json.JsonConverter;
import org.apache.kafka.connect.source.SourceRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 　  * @className: MysqlSource
 * 　　* @description:TODO
 * 　　* @param:
 * 　　* @return:
 * 　　* @throws:
 * 　　* @email lzz215855518@gmail.com
 * 　　* @author: lizz
 * 　　* @date: 2020/12/05 14:03
 */
@SyncDataSourceHandler(value = SyncDataSource.MYSQL)
@Component
@Slf4j
public class MysqlSource extends AbstractSync implements DbzSource {

    @Autowired
    // @Qualifier("keyConverter")
    private JsonConverter keyConverter;

    @Autowired
    //@Qualifier("valueConverter")
    private JsonConverter valueConverter;

    @Value("${sync.file}")
    private String filePath;
    final Properties props = new Properties();

    @Override
    public boolean start(com.ruoyi.system.domain.SyncInstanceConfig syncDataSource) throws IOException {
        final Properties props = new Properties();
        com.ruoyi.system.domain.SyncDataSource sourceDs = syncDataSource.getSourceDs();
        String url = syncDataSource.getSourceDs().getUrl();
        String host = url.substring(url.indexOf("//") + 2, url.lastIndexOf(":"));
        String port = url.substring(url.lastIndexOf(":") + 1, url.lastIndexOf("/"));
        String database = url.substring(url.lastIndexOf("/") + 1, url.length());
        log.info("url={},host={},port={},database={}", url, host, port, database);
        props.setProperty("name", syncDataSource.getName());
        props.setProperty("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore");
        //props.setProperty("offset.storage.file.filename", filePath + "\\" + "offset\\" + syncDataSource.getId() + "\\storage.dat");
        props.setProperty("offset.storage.file.filename", "D:\\21585\\storage.dat");
        props.setProperty("offset.flush.interval.ms", "1000");
        /* begin connector properties */
        props.setProperty("database.hostname", host);
        props.setProperty("database.port", port);
        props.setProperty("database.user", sourceDs.getUsername());
        props.setProperty("database.password", sourceDs.getPassword());
        props.setProperty("database.server.id", syncDataSource.getId() + "899");
        //props.setProperty("database.history.kafka.bootstrap.servers", "101.200.207.178:9092");
        props.setProperty("database.server.name", syncDataSource.getName());
        props.setProperty("database.history",
                "io.debezium.relational.history.FileDatabaseHistory");
        props.setProperty("database.history.file.filename",
                filePath + "\\" + "offset\\" + syncDataSource.getId() + "\\history.dat");

        props.setProperty("connector.class", "io.debezium.connector.mysql.MySqlConnector");
        props.setProperty("database.include.list", database);
        props.setProperty("table.include.list", "tsest123");
        // props.setProperty("snapshot.mode", "schema_only");
/*        final DebeziumEngine<ChangeEvent<String, String>> engine = (DebeziumEngine<ChangeEvent<String, String>>) DebeziumEngine.create(Json.class)
                .using(props).notifying((records, committer) -> {
                    for (ChangeEvent<String, String> r : records) {
                        System.out.println("Key = '" + r.key() + "' value = '" + r.value() + "'");
                      //  log.info(r.key());
                        committer.markProcessed(r);
                    }
                    committer.markBatchFinished();
                }).build();

        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(engine);
        executor.shutdown();*/

        EmbeddedEngine engine = (EmbeddedEngine) EmbeddedEngine.create()
                .using(props)
                .using(this.getClass().getClassLoader())
                .notifying(this::handleRecord)
                .build();

        ExecutorService executor = Executors.newSingleThreadExecutor();

        executor.execute(engine);

        shutdownHook(engine);

        awaitTermination(executor);
        return true;
    }


    /**
     * For every record this method will be invoked.
     */
    private void handleRecord(SourceRecord record) {
        logRecord(record);

        Struct payload = (Struct) record.value();
        if (Objects.isNull(payload)) {
            return;
        }
        String table = Optional.ofNullable(DebeziumRecordUtils.getRecordStructValue(payload, "source"))
                .map(s -> s.getString("table")).orElse(null);

//        // 处理数据DML
        Envelope.Operation operation = DebeziumRecordUtils.getOperation(payload);
        if (Objects.nonNull(operation)) {
            Struct key = (Struct) record.key();
            handleDML(key, payload, table, operation);
            return;
        }
//
//        // 处理结构DDL
        String ddl = getDDL(payload);
        if (StringUtils.isNotBlank(ddl)) {
            handleDDL(ddl);
        }
    }

    private String getDDL(Struct payload) {
        String ddl = DebeziumRecordUtils.getDDL(payload);
        if (StringUtils.isBlank(ddl)) {
            return null;
        }
        String db = DebeziumRecordUtils.getDatabaseName(payload);
/*        if (StringUtils.isBlank(db)) {
            db = embeddedConfig.getString(MySqlConnectorConfig.DATABASE_WHITELIST);
        }*/
        ddl = ddl.replace(db + ".", "");
        ddl = ddl.replace("`" + db + "`.", "");
        return ddl;
    }

    /**
     * 执行数据库ddl语句
     *
     * @param ddl
     */
    private void handleDDL(String ddl) {
        log.info("ddl语句 : {}", ddl);
        try {
            //jdbcTemplate.execute(ddl);
        } catch (Exception e) {
            log.error("数据库操作DDL语句失败，", e);
        }
    }

    /**
     * 处理insert,update,delete等DML语句
     *
     * @param key       表主键修改事件结构
     * @param payload   表正文响应
     * @param table     表名
     * @param operation DML操作类型
     */
    private void handleDML(Struct key, Struct payload, String table, Envelope.Operation operation) {
        AbstractDebeziumSqlProvider provider = DebeziumSqlProviderFactory.getProvider(operation);
        if (Objects.isNull(provider)) {
            log.error("没有找到sql处理器提供者.");
            return;
        }

        String sql = provider.getSql(key, payload, table);
        if (StringUtils.isBlank(sql)) {
            log.error("找不到sql.");
            return;
        }

        try {
            log.info("dml语句 : {}", sql);
            // namedTemplate.update(sql, provider.getSqlParameterMap());
        } catch (Exception e) {
            log.error("数据库DML操作失败，", e);
        }
    }

    /**
     * 打印消息
     *
     * @param record
     */
    private void logRecord(SourceRecord record) {

        log.info("--------------------");
/*        final byte[] payload = valueConverter.fromConnectData("dummy", record.valueSchema(), record.value());
        final byte[] key = keyConverter.fromConnectData("dummy", record.keySchema(), record.key());
        log.info("Publishing Topic --> {}", record.topic());
        log.info("Key --> {}", new String(key));
        log.info("Payload --> {}", new String(payload));*/
    }

    private void shutdownHook(EmbeddedEngine engine) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Requesting embedded engine to shut down");
            engine.stop();
        }));
    }

    private void awaitTermination(ExecutorService executor) {
        try {
            while (!executor.awaitTermination(10L, TimeUnit.SECONDS)) {
                log.info("Waiting another 10 seconds for the embedded engine to shut down");
            }
        } catch (InterruptedException e) {
            Thread.interrupted();
        }
    }
}
