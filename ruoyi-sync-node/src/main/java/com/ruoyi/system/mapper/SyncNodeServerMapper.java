package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.SyncNodeServer;

/**
 * 【请填写功能名称】Mapper接口
 * 
 * @author ruoyi
 * @date 2020-12-13
 */
public interface SyncNodeServerMapper 
{
    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    public SyncNodeServer selectSyncNodeServerById(Long id);

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param syncNodeServer 【请填写功能名称】
     * @return 【请填写功能名称】集合
     */
    public List<SyncNodeServer> selectSyncNodeServerList(SyncNodeServer syncNodeServer);

    /**
     * 新增【请填写功能名称】
     * 
     * @param syncNodeServer 【请填写功能名称】
     * @return 结果
     */
    public int insertSyncNodeServer(SyncNodeServer syncNodeServer);

    /**
     * 修改【请填写功能名称】
     * 
     * @param syncNodeServer 【请填写功能名称】
     * @return 结果
     */
    public int updateSyncNodeServer(SyncNodeServer syncNodeServer);

    /**
     * 删除【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    public int deleteSyncNodeServerById(Long id);

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteSyncNodeServerByIds(Long[] ids);
}
