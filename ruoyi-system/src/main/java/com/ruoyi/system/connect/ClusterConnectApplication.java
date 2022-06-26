package com.ruoyi.system.connect;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.utils.Exit;
import org.apache.kafka.common.utils.Time;
import org.apache.kafka.connect.runtime.Connect;
import org.apache.kafka.connect.runtime.WorkerInfo;
import org.apache.kafka.connect.runtime.distributed.DistributedHerder;
import org.apache.kafka.connect.runtime.rest.RestServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

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
//@Component
public class ClusterConnectApplication implements ApplicationListener<ApplicationStartedEvent> {

    @Autowired
    private WorkerInfo initInfo;
/*    @Autowired
    private Plugins plugins;
    @Autowired
    private DistributedConfig config;*/

    @Autowired
    private RestServer rest;

    /*    @Autowired
        private Worker worker;
        @Autowired
        private StatusBackingStore statusBackingStore;*/
    @Autowired
    private DistributedHerder herder;

    @Override
    public void onApplicationEvent(ApplicationStartedEvent event) {
        try {
            Time time = Time.SYSTEM;
            long initStart = time.hiResClockMs();
            initInfo.logAll();
            log.info("Scanning for plugin classes. This might take a moment ...");
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
