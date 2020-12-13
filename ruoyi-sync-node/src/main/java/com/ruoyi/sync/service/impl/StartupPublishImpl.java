package com.ruoyi.sync.service.impl;

import com.ruoyi.sync.event.StartupPublishEvent;
import com.ruoyi.sync.service.StartupPublish;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;

/**
 * 　  * @className: StartupPublishImpl
 * 　　* @description:TODO
 * 　　* @param:
 * 　　* @return:
 * 　　* @throws:
 * 　　* @author: lizz
 * 　　* @date: 2020/05/06 15:45
 */
@Service
public class StartupPublishImpl implements ApplicationEventPublisherAware, StartupPublish {
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * @Description: 发布后操作
     * @Author: lizz
     * @Date: 2020/5/6 17:21
     * @Reviser:修改人
     * @ReviseDate:修改时间
     * @Revision:修改内容
     **/
    @Override
    public boolean publish() {
        //消息发布
        applicationEventPublisher.publishEvent(new StartupPublishEvent(this));

        return true;
    }

    /**
     * @Description: TODO
     * @Author: lizz
     * @Date: 2020/5/6 17:22
     * @Reviser:修改人
     * @ReviseDate:修改时间
     * @Revision:修改内容
     **/
    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
