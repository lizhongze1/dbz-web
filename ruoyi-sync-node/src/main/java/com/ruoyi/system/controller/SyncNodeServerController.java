package com.ruoyi.system.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.SyncNodeServer;
import com.ruoyi.system.service.ISyncNodeServerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 【请填写功能名称】Controller
 * 
 * @author ruoyi
 * @date 2020-12-13
 */
@RestController
@RequestMapping("/system/server")
public class SyncNodeServerController extends BaseController
{
    @Autowired
    private ISyncNodeServerService syncNodeServerService;

    /**
     * 查询【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:server:list')")
    @GetMapping("/list")
    public TableDataInfo list(SyncNodeServer syncNodeServer)
    {
        startPage();
        List<SyncNodeServer> list = syncNodeServerService.selectSyncNodeServerList(syncNodeServer);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:server:export')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SyncNodeServer syncNodeServer)
    {
        List<SyncNodeServer> list = syncNodeServerService.selectSyncNodeServerList(syncNodeServer);
        ExcelUtil<SyncNodeServer> util = new ExcelUtil<SyncNodeServer>(SyncNodeServer.class);
        return util.exportExcel(list, "server");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:server:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(syncNodeServerService.selectSyncNodeServerById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:server:add')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SyncNodeServer syncNodeServer)
    {
        return toAjax(syncNodeServerService.insertSyncNodeServer(syncNodeServer));
    }

    /**
     * 修改【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:server:edit')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SyncNodeServer syncNodeServer)
    {
        return toAjax(syncNodeServerService.updateSyncNodeServer(syncNodeServer));
    }

    /**
     * 删除【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:server:remove')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(syncNodeServerService.deleteSyncNodeServerByIds(ids));
    }
}
