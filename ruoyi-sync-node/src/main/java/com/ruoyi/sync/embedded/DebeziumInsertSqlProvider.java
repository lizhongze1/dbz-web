package com.ruoyi.sync.embedded;

import com.ruoyi.common.DMLEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.function.Function;

/**
 * 　  * @className: DebeziumInsertSqlProvider
 * 　　* @description:TODO
 * 　　* @param:
 * 　　* @return:
 * 　　* @throws:
 * 　　* @email lzz215855518@gmail.com
 * 　　* @author: lizz
 * 　　* @date: 2022/04/01 20:58
 */
public class DebeziumInsertSqlProvider extends AbstractDebeziumSqlProvider {
    @Override
    protected boolean needParseColumn() {
        return true;
    }

    @Override
    protected boolean needParsePrimaryKey() {
        return false;
    }

    @Override
    protected Function<String, String> getColumnNameFunction() {
        return columnName -> ":" + columnName;
    }

    @Override
    protected String generateSql(String table) {
        return DMLEnum.INSERT_SQL.generateSQL(table, StringUtils.join(preparedColumnList, ","));
    }
}
