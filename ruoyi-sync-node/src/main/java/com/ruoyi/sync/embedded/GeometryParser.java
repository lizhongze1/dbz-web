package com.ruoyi.sync.embedded;

import com.ruoyi.common.CharUtils;
import com.ruoyi.sync.embedded.parser.DebeziumParser;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.data.Struct;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 　  * @className: GeometryParser
 * 　　* @description:TODO
 * 　　* @param:
 * 　　* @return:
 * 　　* @throws:
 * 　　* @email lzz215855518@gmail.com
 * 　　* @author: lizz
 * 　　* @date: 2022/04/01 13:04
 */
public class GeometryParser implements DebeziumParser<Object, byte[]> {

    @Override
    public byte[] parse(Schema schema, Object value) {
        List<byte[]> geo = schema.fields().stream()
                .map(field -> {
                    Object o = ((Struct) value).get(field);
                    if (o == null) {
                        return CharUtils.intToBytes(0);
                    }
                    return (byte[]) o;
                })
                .sorted(Comparator.comparing(array -> array.length))
                .collect(Collectors.toList());
        return CharUtils.byteMergerAll(geo);
    }
}
