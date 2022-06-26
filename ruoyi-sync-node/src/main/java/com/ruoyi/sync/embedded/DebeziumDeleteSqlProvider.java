package com.ruoyi.sync.embedded;

import com.ruoyi.common.DMLEnum;
import org.apache.commons.lang3.StringUtils;

/**
 * 　  * @className: DebeziumDeleteSqlProvider
 * 　　* @description:TODO
 * 　　* @param:
 * 　　* @return:
 * 　　* @throws:
 * 　　* @email lzz215855518@gmail.com
 * 　　* @author: lizz
 * 　　* @date: 2022/04/01 17:03
 */
public class DebeziumDeleteSqlProvider extends AbstractDebeziumSqlProvider {

    @Override
    protected boolean needParseColumn() {
        return false;
    }

    @Override
    protected boolean needParsePrimaryKey() {
        return true;
    }

    @Override
    protected String generateSql(String table) {
        return DMLEnum.DELETE_SQL.generateSQL(table, StringUtils.join(preparedPrimaryKeyList, " and "));
    }
}
