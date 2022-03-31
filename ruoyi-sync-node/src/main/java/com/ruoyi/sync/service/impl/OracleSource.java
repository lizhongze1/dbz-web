package com.ruoyi.sync.service.impl;

import com.ruoyi.sync.annotation.SyncDataSourceHandler;
import com.ruoyi.sync.enums.SyncDataSource;
import com.ruoyi.sync.service.DbzSource;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 *
 * 　  * @className: OracleSource
 * 　　* @description:TODO
 * 　　* @param:
 * 　　* @return:
 * 　　* @throws:
 * 　　* @email lzz215855518@gmail.com
 * 　　* @author: lizz
 * 　　* @date: 2020/12/05 14:03
 *
 */
@SyncDataSourceHandler(value = SyncDataSource.ORACLE)
@Component
public class OracleSource extends AbstractSync implements DbzSource {
    @Override
    public boolean start(com.ruoyi.system.domain.SyncInstanceConfig syncDataSource) throws IOException {
        return false;
    }
}
