package com.ruoyi.sync.listener;

import com.ruoyi.common.utils.thread.NamedThreadFactory;
import com.ruoyi.sync.service.DbzSync;
import com.ruoyi.sync.service.impl.MysqlSync;
import com.ruoyi.system.domain.SyncNodeServer;
import com.ruoyi.system.mapper.SyncNodeServerMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

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
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {
    @Value("${sync.ip}")
    private String ip;
    @Value("${sync.port}")
    private String port;
    @Autowired
    com.ruoyi.system.mapper.SyncNodeServerMapper syncNodeServerMapper;

    private static ScheduledExecutorService executor = Executors.newScheduledThreadPool(1,
            new NamedThreadFactory("dbz-server-scan"));

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            SyncNodeServer syncNodeServer=new SyncNodeServer();
            List<SyncNodeServer> syncNodeServers= syncNodeServerMapper.selectSyncNodeServerList(syncNodeServer);
            Consumer consumer = StartupListener::start;
            consumer.accept(this);
        }
    }

    private static void start(Object o) {

        DbzSync DbzSync=new MysqlSync();
        try {
            DbzSync.start();
        }catch (Exception e){

        }

    /*    executor.scheduleWithFixedDelay(new Runnable() {

           *//* private PlainCanal lastCanalConfig;
*//*
            public void run() {
                try {


                } catch (Throwable e) {
                    log.error("scan failed", e);
                }
            }

        }, 0, 5, TimeUnit.SECONDS);*/
    }


}
