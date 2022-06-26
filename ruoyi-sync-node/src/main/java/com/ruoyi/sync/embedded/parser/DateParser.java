package com.ruoyi.sync.embedded.parser;

import org.apache.kafka.connect.data.Schema;

import java.time.LocalDate;

/**
 * 　  * @className: DateParser
 * 　　* @description:TODO
 * 　　* @param:
 * 　　* @return:
 * 　　* @throws:
 * 　　* @email lzz215855518@gmail.com
 * 　　* @author: lizz
 * 　　* @date: 2022/04/01 12:01
 */
public class DateParser implements DebeziumParser<Object, LocalDate> {

    @Override
    public LocalDate parse(Schema schema, Object value) {
        return LocalDate.ofEpochDay((long) (int) value + 1);
    }
}
