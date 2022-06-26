package com.ruoyi.datasource.config;

import org.apache.kafka.connect.json.JsonConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 　  * @className: SyncConfig
 * 　　* @description:TODO
 * 　　* @param:
 * 　　* @return:
 * 　　* @throws:
 * 　　* @email lzz215855518@gmail.com
 * 　　* @author: lizz
 * 　　* @date: 2022/04/01 9:38
 */
@Configuration
public class SyncConfig {
    @Bean
    JsonConverter keyConverter() {
        return new JsonConverter();
    }

    @Bean
    JsonConverter valueConverter() {
        return new JsonConverter();
    }
}
