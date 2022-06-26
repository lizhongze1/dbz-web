package com.ruoyi.sync.embedded.parser;

import org.apache.kafka.connect.data.Schema;

/**
 * 　  * @className: DebeziumParser
 * 　　* @description:TODO
 * 　　* @param:
 * 　　* @return:
 * 　　* @throws:
 * 　　* @email lzz215855518@gmail.com
 * 　　* @author: lizz
 * 　　* @date: 2022/03/01 12:49
 */
@FunctionalInterface
public interface DebeziumParser<T, R> {
    R parse(Schema schema, T t);
}
