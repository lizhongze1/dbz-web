package com.ruoyi.sync.enums;

/**
 *
 * 　  * @className: SyncDataSourceHandler
 * 　　* @description:TODO
 * 　　* @param:
 * 　　* @return:
 * 　　* @throws:
 * 　　* @email lzz215855518@gmail.com
 * 　　* @author: lizz
 * 　　* @date: 2020/12/13 18:29
 *
 */
public enum SyncDataSource {
    MYSQL("1", "MYSQL"),
    ORACLE("2", "ORACLE"),
    SLQSERVER("3", "SQLSERVER");
    public final String code;
    public final String name;

    SyncDataSource(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
