package com.ruoyi.sync.service;

import com.ruoyi.system.domain.SyncDataSource;
import com.ruoyi.system.domain.SyncInstanceConfig;

import java.io.IOException;

/**
 *
 * 　  * @className: DbzSync
 * 　　* @description:TODO
 * 　　* @param:
 * 　　* @return:
 * 　　* @throws:
 * 　　* @email lzz215855518@gmail.com
 * 　　* @author: lizz
 * 　　* @date: 2020/12/05 14:05
 *
 */
public interface DbzSync {
     boolean start(SyncInstanceConfig syncDataSource) throws IOException;
     boolean stop();
}
