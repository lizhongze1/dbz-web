package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.SyncNodeServerMapper;
import com.ruoyi.system.domain.SyncNodeServer;
import com.ruoyi.system.service.ISyncNodeServerService;

/**
 * 【请填写功能名称】Service业务层处理
 * 
 * @author ruoyi
 * @date 2020-12-13
 */
@Service
public class SyncNodeServerServiceImpl implements ISyncNodeServerService 
{
    @Autowired
    private SyncNodeServerMapper syncNodeServerMapper;

    /**
     * 查询【请填写功能名称】
     * 
     * @param id 【请填写功能名称】ID
     * @return 【请填写功能名称】
     */
    @Override
    public SyncNodeServer selectSyncNodeServerById(Long id)
    {
        return syncNodeServerMapper.selectSyncNodeServerById(id);
    }

    /**
     * 查询【请填写功能名称】列表
     * 
     * @param syncNodeServer 【请填写功能名称】
     * @return 【请填写功能名称】
     */
    @Override
    public List<SyncNodeServer> selectSyncNodeServerList(SyncNodeServer syncNodeServer)
    {
        return syncNodeServerMapper.selectSyncNodeServerList(syncNodeServer);
    }

    /**
     * 新增【请填写功能名称】
     * 
     * @param syncNodeServer 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int insertSyncNodeServer(SyncNodeServer syncNodeServer)
    {
        return syncNodeServerMapper.insertSyncNodeServer(syncNodeServer);
    }

    /**
     * 修改【请填写功能名称】
     * 
     * @param syncNodeServer 【请填写功能名称】
     * @return 结果
     */
    @Override
    public int updateSyncNodeServer(SyncNodeServer syncNodeServer)
    {
        return syncNodeServerMapper.updateSyncNodeServer(syncNodeServer);
    }

    /**
     * 批量删除【请填写功能名称】
     * 
     * @param ids 需要删除的【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteSyncNodeServerByIds(Long[] ids)
    {
        return syncNodeServerMapper.deleteSyncNodeServerByIds(ids);
    }

    /**
     * 删除【请填写功能名称】信息
     * 
     * @param id 【请填写功能名称】ID
     * @return 结果
     */
    @Override
    public int deleteSyncNodeServerById(Long id)
    {
        return syncNodeServerMapper.deleteSyncNodeServerById(id);
    }
}
