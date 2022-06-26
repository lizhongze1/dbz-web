package com.ruoyi.sync.embedded.parser;

import org.apache.kafka.connect.data.Schema;
import org.apache.pulsar.shade.org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * 　  * @className: TimestampParser
 * 　　* @description:TODO
 * 　　* @param:
 * 　　* @return:
 * 　　* @throws:
 * 　　* @email lzz215855518@gmail.com
 * 　　* @author: lizz
 * 　　* @date: 2022/03/01 12:54
 */
public class TimestampParser implements DebeziumParser<Object, String> {
    @Override
    public String parse(Schema schema, Object value) {
        return DateFormatUtils.format(new Date((long) value), "yyyy-MM-dd HH:mm:ss");
    }
}
