package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SyncInstanceConfig;

/**
 * 【请填写功能名称】Mapper接口
 *
 * @author ruoyi
 * @date 2020-12-13
 */
public interface SyncInstanceConfigMapper
{
    /**
     * 查询【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public SyncInstanceConfig selectSyncInstanceConfigById(Long id);

    /**
     * 查询【请填写功能名称】列表
     *
     * @param syncInstanceConfig 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<SyncInstanceConfig> selectSyncInstanceConfigList(SyncInstanceConfig syncInstanceConfig);

    public List<SyncInstanceConfig> getSyncInstanceConfigList(SyncInstanceConfig syncInstanceConfig);

    /**
     * 新增【请填写功能名称】
     *
     * @param syncInstanceConfig 【请填写功能名称】
     * @return 结果
     */
    public int insertSyncInstanceConfig(SyncInstanceConfig syncInstanceConfig);

    /**
     * 修改【请填写功能名称】
     *
     * @param syncInstanceConfig 【请填写功能名称】
     * @return 结果
     */
    public int updateSyncInstanceConfig(SyncInstanceConfig syncInstanceConfig);

    /**
     * 删除【请填写功能名称】
     *
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteSyncInstanceConfigById(Long id);

    /**
     * 批量删除【请填写功能名称】
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSyncInstanceConfigByIds(Long[] ids);

    public SyncInstanceConfig findSelfInstanceInfo();
}
