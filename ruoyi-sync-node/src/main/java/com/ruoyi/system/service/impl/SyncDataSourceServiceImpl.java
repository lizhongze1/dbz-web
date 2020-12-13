package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SyncDataSourceMapper;
import com.ruoyi.system.domain.SyncDataSource;
import com.ruoyi.system.service.ISyncDataSourceService;

/**
 * 数据源维护Service业务层处理
 * 
 * @author lizz
 * @date 2020-12-13
 */
@Service
public class SyncDataSourceServiceImpl implements ISyncDataSourceService 
{
    @Autowired
    private SyncDataSourceMapper syncDataSourceMapper;

    /**
     * 查询数据源维护
     * 
     * @param id 数据源维护ID
     * @return 数据源维护
     */
    @Override
    public SyncDataSource selectSyncDataSourceById(Long id)
    {
        return syncDataSourceMapper.selectSyncDataSourceById(id);
    }

    /**
     * 查询数据源维护列表
     * 
     * @param syncDataSource 数据源维护
     * @return 数据源维护
     */
    @Override
    public List<SyncDataSource> selectSyncDataSourceList(SyncDataSource syncDataSource)
    {
        return syncDataSourceMapper.selectSyncDataSourceList(syncDataSource);
    }

    /**
     * 新增数据源维护
     * 
     * @param syncDataSource 数据源维护
     * @return 结果
     */
    @Override
    public int insertSyncDataSource(SyncDataSource syncDataSource)
    {
        return syncDataSourceMapper.insertSyncDataSource(syncDataSource);
    }

    /**
     * 修改数据源维护
     * 
     * @param syncDataSource 数据源维护
     * @return 结果
     */
    @Override
    public int updateSyncDataSource(SyncDataSource syncDataSource)
    {
        return syncDataSourceMapper.updateSyncDataSource(syncDataSource);
    }

    /**
     * 批量删除数据源维护
     * 
     * @param ids 需要删除的数据源维护ID
     * @return 结果
     */
    @Override
    public int deleteSyncDataSourceByIds(Long[] ids)
    {
        return syncDataSourceMapper.deleteSyncDataSourceByIds(ids);
    }

    /**
     * 删除数据源维护信息
     * 
     * @param id 数据源维护ID
     * @return 结果
     */
    @Override
    public int deleteSyncDataSourceById(Long id)
    {
        return syncDataSourceMapper.deleteSyncDataSourceById(id);
    }
}
