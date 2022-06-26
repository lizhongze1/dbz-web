package com.ruoyi.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 　  * @className: DMLEnum
 * 　　* @description:TODO
 * 　　* @param:
 * 　　* @return:
 * 　　* @throws:
 * 　　* @email lzz215855518@gmail.com
 * 　　* @author: lizz
 * 　　* @date: 2022/04/01 12:57
 */
@Getter
@AllArgsConstructor
public enum DMLEnum {

    INSERT_SQL(Constants.INSERT_SQL),
    UPDATE_SQL(Constants.UPDATE_SQL),
    DELETE_SQL(Constants.DELETE_SQL);

    private String sqlFormatter;

    public String generateSQL(Object... args) {
        return String.format(getSqlFormatter(), args);
    }
}
