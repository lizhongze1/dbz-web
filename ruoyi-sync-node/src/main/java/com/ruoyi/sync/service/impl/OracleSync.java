package com.ruoyi.sync.service.impl;

import com.ruoyi.sync.annotation.SyncDataSourceHandler;
import com.ruoyi.sync.enums.SyncDataSource;
import com.ruoyi.sync.service.DbzSync;

/**
 *
 * 　  * @className: OracleSync
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
public class OracleSync extends AbstractSync implements DbzSync {
}
