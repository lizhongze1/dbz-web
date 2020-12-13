package com.ruoyi.datasource.support;

import com.alibaba.druid.pool.DruidDataSource;
import com.ruoyi.system.domain.SyncDataSource;
import com.ruoyi.system.domain.SyncInstanceConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * 　  * @className: DataSourcePool
 * 　　* @description:TODO
 * 　　* @param:
 * 　　* @return:
 * 　　* @throws:
 * 　　* @email lzz215855518@gmail.com
 * 　　* @author: lizz
 * 　　* @date: 2020/12/13 18:59
 *
 */
@Component
@Slf4j
public class DataSourcePool {
    public void initDataSourcePool(SyncDataSource syncDataSource){
        DruidDataSource ds = new DruidDataSource();
        ds.setDriverClassName(syncDataSource.getDriver());
        ds.setUrl(syncDataSource.getUrl()+"?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&failOverReadOnly=false");
        ds.setUsername(syncDataSource.getUsername());
        ds.setPassword(syncDataSource.getPassword());
        ds.setInitialSize(1);
        ds.setMinIdle(1);
        ds.setMaxActive(1000);
        ds.setMaxWait(60000);
        ds.setTimeBetweenEvictionRunsMillis(60000);
        ds.setMinEvictableIdleTimeMillis(300000);
        ds.setValidationQuery("select 1");
        try {
            ds.init();
        } catch (SQLException e) {
            log.error("初始化连池处错误{}",e);
        }
        DatasourceConfig.DATA_SOURCES.put(syncDataSource.getId().toString(), ds);
    }
}
