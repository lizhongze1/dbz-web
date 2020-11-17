package com.ruoyi.system.source.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.source.mapper.DbSourceMapper;
import com.ruoyi.system.source.domain.DbSource;
import com.ruoyi.system.source.service.IDbSourceService;

/**
 * 数据源Service业务层处理
 * 
 * @author lizz
 * @date 2020-11-17
 */
@Service
public class DbSourceServiceImpl implements IDbSourceService 
{
    @Autowired
    private DbSourceMapper dbSourceMapper;

    /**
     * 查询数据源
     * 
     * @param id 数据源ID
     * @return 数据源
     */
    @Override
    public DbSource selectDbSourceById(String id)
    {
        return dbSourceMapper.selectDbSourceById(id);
    }

    /**
     * 查询数据源列表
     * 
     * @param dbSource 数据源
     * @return 数据源
     */
    @Override
    public List<DbSource> selectDbSourceList(DbSource dbSource)
    {
        return dbSourceMapper.selectDbSourceList(dbSource);
    }

    /**
     * 新增数据源
     * 
     * @param dbSource 数据源
     * @return 结果
     */
    @Override
    public int insertDbSource(DbSource dbSource)
    {
        return dbSourceMapper.insertDbSource(dbSource);
    }

    /**
     * 修改数据源
     * 
     * @param dbSource 数据源
     * @return 结果
     */
    @Override
    public int updateDbSource(DbSource dbSource)
    {
        return dbSourceMapper.updateDbSource(dbSource);
    }

    /**
     * 批量删除数据源
     * 
     * @param ids 需要删除的数据源ID
     * @return 结果
     */
    @Override
    public int deleteDbSourceByIds(String[] ids)
    {
        return dbSourceMapper.deleteDbSourceByIds(ids);
    }

    /**
     * 删除数据源信息
     * 
     * @param id 数据源ID
     * @return 结果
     */
    @Override
    public int deleteDbSourceById(String id)
    {
        return dbSourceMapper.deleteDbSourceById(id);
    }
}
