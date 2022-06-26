package com.ruoyi.sync.embedded.parser;

import com.google.common.collect.Maps;
import com.ruoyi.sync.embedded.GeometryParser;

import java.util.Map;

/**
 * 　  * @className: ParserFactory
 * 　　* @description:TODO
 * 　　* @param:
 * 　　* @return:
 * 　　* @throws:
 * 　　* @email lzz215855518@gmail.com
 * 　　* @author: lizz
 * 　　* @date: 2022/03/01 12:50
 */
public class ParserFactory {

    private static Map<String, DebeziumParser> parserMap = Maps.newHashMap();

    static {
        parserMap.put(io.debezium.time.ZonedTimestamp.SCHEMA_NAME, new ZonedTimestampParser());
        parserMap.put(io.debezium.time.Timestamp.SCHEMA_NAME, new TimestampParser());
        parserMap.put(io.debezium.time.Date.SCHEMA_NAME, new DateParser());
        parserMap.put(io.debezium.time.MicroTime.SCHEMA_NAME, new MicroTimeParser());
        parserMap.put(io.debezium.data.geometry.Geometry.LOGICAL_NAME, new GeometryParser());
        parserMap.put(io.debezium.data.geometry.Point.LOGICAL_NAME, new PointParser());
    }

    public static DebeziumParser getParser(String schemaName) {
        return parserMap.get(schemaName);
    }
}
