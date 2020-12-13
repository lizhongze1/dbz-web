package com.ruoyi.sync.listener;

import com.ruoyi.common.utils.thread.NamedThreadFactory;
import com.ruoyi.sync.event.StartupPublishEvent;
import com.ruoyi.sync.service.DbzSync;
import com.ruoyi.system.domain.SyncInstanceConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 　  * @className: StartupListener
 * 　　* @description:TODO
 * 　　* @param:
 * 　　* @return:
 * 　　* @throws:
 * 　　* @email lzz215855518@gmail.com
 * 　　* @author: lizz
 * 　　* @date: 2020/12/05 13:34
 */
@Slf4j
@Component
public class StartupListener implements ApplicationListener<StartupPublishEvent> {
    @Value("${sync.ip}")
    private String ip;
    @Value("${sync.port}")
    private long port;
    @Autowired
    com.ruoyi.system.service.ISyncInstanceConfigService syncInstanceConfigService;
    @Autowired
    com.ruoyi.sync.service.SyncDataSourceContext syncDataSourceContext;

    @Autowired
    com.ruoyi.system.service.ISyncDataSourceService syncDataSourceService;
    @Autowired
    com.ruoyi.datasource.support.DataSourcePool dataSourcePool;

    private static ScheduledExecutorService executor = Executors.newScheduledThreadPool(1,
            new NamedThreadFactory("dbz-server-scan"));

    @Override
    public void onApplicationEvent(StartupPublishEvent contextRefreshedEvent) {

        SyncInstanceConfig syncInstanceConfig = new SyncInstanceConfig();
        syncInstanceConfig.setIp(ip);
        syncInstanceConfig.setTcpPort(port);
        List<SyncInstanceConfig> syncNodeServers = syncInstanceConfigService.getSyncInstanceConfigList(syncInstanceConfig);

        if (syncNodeServers.size() > 0) {
            for (SyncInstanceConfig syncInstance : syncNodeServers
            ) {
                //初始化连接池
                try {
                    dataSourcePool.initDataSourcePool(syncInstance.getTargetDs());
                } catch (Exception e) {

                    log.info("初始化连接池失败{}", e);
                    continue;
                }
                DbzSync dbzSync = syncDataSourceContext.getDbzSync(syncInstance.getTargetDs().getType());
                try {
                    dbzSync.start(syncInstance);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


        }

/*            Consumer consumer = StartupListener::start;
            consumer.accept(this);*/
    }


   /* private static void start(Object o) {

        DbzSync DbzSync = new MysqlSync();
        try {
            DbzSync.start();
        } catch (Exception e) {

        }

        *//*    executor.scheduleWithFixedDelay(new Runnable() {

     *//**//* private PlainCanal lastCanalConfig;
     *//**//*
            public void run() {
                try {


                } catch (Throwable e) {
                    log.error("scan failed", e);
                }
            }

        }, 0, 5, TimeUnit.SECONDS);*//*
    }*/


}
