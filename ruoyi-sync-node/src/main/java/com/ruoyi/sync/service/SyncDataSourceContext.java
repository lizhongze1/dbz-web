package com.ruoyi.sync.service;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 　  * @className: SyncDataSourceContext
 * 　　* @description:TODO
 * 　　* @param:
 * 　　* @return:
 * 　　* @throws:
 * 　　* @email lzz215855518@gmail.com
 * 　　* @author: lizz
 * 　　* @date: 2020/12/13 18:37
 */
@Component
public class SyncDataSourceContext {
    private final Map<String, DbzSync> handlerMap = new HashMap<>();

    public DbzSync getDbzSync(String type) {
        return handlerMap.get(type);
    }

    public void putDbzSync(String code, DbzSync dbzSync) {
        handlerMap.put(code, dbzSync);
    }
}
