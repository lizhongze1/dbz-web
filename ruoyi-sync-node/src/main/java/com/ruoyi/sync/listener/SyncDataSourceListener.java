package com.ruoyi.sync.listener;


import com.ruoyi.sync.annotation.SyncDataSourceHandler;
import com.ruoyi.sync.service.DbzSource;
import com.ruoyi.sync.service.SyncDataSourceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 　  * @className: SyncDataSourceListener
 * 　　* @description:TODO
 * 　　* @param:
 * 　　* @return:
 * 　　* @throws:
 * 　　* @email lzz215855518@gmail.com
 * 　　* @author: lizz
 * 　　* @date: 2020/12/13 18:27
 */
@Component
public class SyncDataSourceListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private com.ruoyi.sync.service.StartupPublish startupPublish;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        Map<String, Object> beans = contextRefreshedEvent.getApplicationContext().getBeansWithAnnotation(SyncDataSourceHandler.class);
        SyncDataSourceContext serviceContext = contextRefreshedEvent.getApplicationContext().getBean(SyncDataSourceContext.class);
        beans.forEach((name, bean) -> {
            SyncDataSourceHandler typeHandler = bean.getClass().getAnnotation(SyncDataSourceHandler.class);
            serviceContext.putDbzSync(typeHandler.value().code, (DbzSource) bean);
        });
        startupPublish.publish();
    }
}
