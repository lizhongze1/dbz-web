package com.ruoyi.sync.embedded.parser;

import org.apache.kafka.connect.data.Schema;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

/**
 * 　  * @className: MicroTimeParser
 * 　　* @description:TODO
 * 　　* @param:
 * 　　* @return:
 * 　　* @throws:
 * 　　* @email lzz215855518@gmail.com
 * 　　* @author: lizz
 * 　　* @date: 2022/04/01 13:03
 */
public class MicroTimeParser implements DebeziumParser<Object, LocalTime> {


    @Override
    public LocalTime parse(Schema schema, Object value) {
        return LocalTime.ofNanoOfDay((long) value * TimeUnit.MICROSECONDS.toNanos(1));
    }
}
