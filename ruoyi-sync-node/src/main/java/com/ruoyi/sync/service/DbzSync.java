package com.ruoyi.sync.service;

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
     boolean start() throws IOException;
     boolean stop();
}
