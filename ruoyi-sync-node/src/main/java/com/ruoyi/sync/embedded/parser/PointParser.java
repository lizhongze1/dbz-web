package com.ruoyi.sync.embedded.parser;


import com.ruoyi.common.CharUtils;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 　  * @className: PointParser
 * 　　* @description:TODO
 * 　　* @param:
 * 　　* @return:
 * 　　* @throws:
 * 　　* @email lzz215855518@gmail.com
 * 　　* @author: lizz
 * 　　* @date: 2022/04/01 12:55
 */
public class PointParser implements DebeziumParser<Object, byte[]> {
    @Override
    public byte[] parse(Schema schema, Object value) {
        List<byte[]> geo = schema.fields().stream()
                .map(field -> {
                    Object o = ((Struct) value).get(field);
                    if (o == null) {
                        return CharUtils.intToBytes(0);
                    } else if (o instanceof Double) {
                        return null;
                    }
                    return (byte[]) o;
                })
                .filter(Objects::nonNull)
                .sorted(Comparator.comparing(array -> array.length))
                .collect(Collectors.toList());
        return CharUtils.byteMergerAll(geo);
    }
}
