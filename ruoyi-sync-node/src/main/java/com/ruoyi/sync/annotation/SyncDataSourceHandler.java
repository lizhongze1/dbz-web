package com.ruoyi.sync.annotation;

import com.ruoyi.sync.enums.SyncDataSource;

import java.lang.annotation.*;

/**
 *
 * 　  * @className: SyncDataSourceHandler
 * 　　* @description:TODO
 * 　　* @param:
 * 　　* @return:
 * 　　* @throws:
 * 　 * @email lzz215855518@gmail.com
 * 　　* @author: lizz
 * 　　* @date: 2020/12/13 18:34
 *
 */
@Documented
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SyncDataSourceHandler {
    SyncDataSource value();
}
