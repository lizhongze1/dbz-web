package com.ruoyi.system.source.mapper;

import java.util.List;
import com.ruoyi.system.source.domain.DbSource;

/**
 * 数据源Mapper接口
 * 
 * @author lizz
 * @date 2020-11-17
 */
public interface DbSourceMapper 
{
    /**
     * 查询数据源
     * 
     * @param id 数据源ID
     * @return 数据源
     */
    public DbSource selectDbSourceById(String id);

    /**
     * 查询数据源列表
     * 
     * @param dbSource 数据源
     * @return 数据源集合
     */
    public List<DbSource> selectDbSourceList(DbSource dbSource);

    /**
     * 新增数据源
     * 
     * @param dbSource 数据源
     * @return 结果
     */
    public int insertDbSource(DbSource dbSource);

    /**
     * 修改数据源
     * 
     * @param dbSource 数据源
     * @return 结果
     */
    public int updateDbSource(DbSource dbSource);

    /**
     * 删除数据源
     * 
     * @param id 数据源ID
     * @return 结果
     */
    public int deleteDbSourceById(String id);

    /**
     * 批量删除数据源
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDbSourceByIds(String[] ids);
}
