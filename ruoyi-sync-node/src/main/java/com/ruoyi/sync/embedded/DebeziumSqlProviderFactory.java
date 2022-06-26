package com.ruoyi.sync.embedded;

import com.google.common.collect.Maps;
import io.debezium.data.Envelope;

import java.util.Map;

/**
 * 　  * @className: DebeziumSqlProviderFactory
 * 　　* @description:TODO
 * 　　* @param:
 * 　　* @return:
 * 　　* @throws:
 * 　　* @email lzz215855518@gmail.com
 * 　　* @author: lizz
 * 　　* @date: 2022/04/01 13:05
 */
public class DebeziumSqlProviderFactory {
    private static Map<Envelope.Operation, AbstractDebeziumSqlProvider> map = Maps.newHashMap();

    static {
        map.put(Envelope.Operation.CREATE, new DebeziumInsertSqlProvider());
        map.put(Envelope.Operation.UPDATE, new DebeziumUpdateSqlProvider());
        map.put(Envelope.Operation.DELETE, new DebeziumDeleteSqlProvider());
    }

    public static AbstractDebeziumSqlProvider getProvider(Envelope.Operation operation) {
        return map.get(operation);
    }

}
