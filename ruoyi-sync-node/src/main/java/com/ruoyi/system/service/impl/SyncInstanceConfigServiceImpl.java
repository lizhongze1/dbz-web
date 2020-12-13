package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SyncInstanceConfigMapper;
import com.ruoyi.system.domain.SyncInstanceConfig;
import com.ruoyi.system.service.ISyncInstanceConfigService;

/**
 * 【请填写功能名称】Service业务层处理
 *
 * @author ruoyi
 * @date 2020-12-13
 */
@Service
public class SyncInstanceConfigServiceImpl implements ISyncInstanceConfigService
{
    @Autowired
    private SyncInstanceConfigMapper syncInstanceConfigMapper;

    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public SyncInstanceConfig selectSyncInstanceConfigById(Long id)
    {
        return syncInstanceConfigMapper.selectSyncInstanceConfigById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     *
     * @param syncInstanceConfig 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<SyncInstanceConfig> selectSyncInstanceConfigList(SyncInstanceConfig syncInstanceConfig)
    {
        return syncInstanceConfigMapper.selectSyncInstanceConfigList(syncInstanceConfig);
    }

    @Override
    public List<SyncInstanceConfig> getSyncInstanceConfigList(SyncInstanceConfig syncInstanceConfig)
    {
        return syncInstanceConfigMapper.getSyncInstanceConfigList(syncInstanceConfig);
    }


    /**
     * 新增【请填写功能名称】
     *
     * @param syncInstanceConfig 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertSyncInstanceConfig(SyncInstanceConfig syncInstanceConfig)
    {
        return syncInstanceConfigMapper.insertSyncInstanceConfig(syncInstanceConfig);
    }

    /**
     * 修改【请填写功能名称】
     *
     * @param syncInstanceConfig 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateSyncInstanceConfig(SyncInstanceConfig syncInstanceConfig)
    {
        return syncInstanceConfigMapper.updateSyncInstanceConfig(syncInstanceConfig);
    }

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteSyncInstanceConfigByIds(Long[] ids)
    {
        return syncInstanceConfigMapper.deleteSyncInstanceConfigByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     *
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteSyncInstanceConfigById(Long id)
    {
        return syncInstanceConfigMapper.deleteSyncInstanceConfigById(id);
    }
}
