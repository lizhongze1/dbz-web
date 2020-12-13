package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.SyncDataSource;

/**
 * 数据源维护Service接口
 * 
 * @author lizz
 * @date 2020-12-13
 */
public interface ISyncDataSourceService 
{
    /**
     * 查询数据源维护
     * 
     * @param id 数据源维护ID
     * @return 数据源维护
     */
    public SyncDataSource selectSyncDataSourceById(Long id);

    /**
     * 查询数据源维护列表
     * 
     * @param syncDataSource 数据源维护
     * @return 数据源维护集合
     */
    public List<SyncDataSource> selectSyncDataSourceList(SyncDataSource syncDataSource);

    /**
     * 新增数据源维护
     * 
     * @param syncDataSource 数据源维护
     * @return 结果
     */
    public int insertSyncDataSource(SyncDataSource syncDataSource);

    /**
     * 修改数据源维护
     * 
     * @param syncDataSource 数据源维护
     * @return 结果
     */
    public int updateSyncDataSource(SyncDataSource syncDataSource);

    /**
     * 批量删除数据源维护
     * 
     * @param ids 需要删除的数据源维护ID
     * @return 结果
     */
    public int deleteSyncDataSourceByIds(Long[] ids);

    /**
     * 删除数据源维护信息
     * 
     * @param id 数据源维护ID
     * @return 结果
     */
    public int deleteSyncDataSourceById(Long id);
}
