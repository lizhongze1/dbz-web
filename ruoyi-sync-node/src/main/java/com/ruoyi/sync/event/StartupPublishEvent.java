package com.ruoyi.sync.event;

import org.springframework.context.ApplicationEvent;

/**
 * 　  * @className: StartupPublishEvent
 * 　　* @description:发布通知运行监听
 * 　　* @param:
 * 　　* @return:
 * 　　* @throws:
 * 　　* @author: lizz
 * 　　* @date: 2020/05/06 15:31
 */
public class StartupPublishEvent extends ApplicationEvent {
    private static final long serialVersionUID = -5481658020206295565L;
    private StartupPublishEvent riskEarlyWarn;

    public StartupPublishEvent(Object source) {
        super(source);
    }

    public StartupPublishEvent getEarlyWarnInfo() {
        return riskEarlyWarn;
    }
}
