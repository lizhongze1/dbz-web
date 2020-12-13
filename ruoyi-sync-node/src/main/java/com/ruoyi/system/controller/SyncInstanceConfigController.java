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
import com.ruoyi.system.domain.SyncInstanceConfig;
import com.ruoyi.system.service.ISyncInstanceConfigService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 【请填写功能名称】Controller
 * 
 * @author ruoyi
 * @date 2020-12-13
 */
@RestController
@RequestMapping("/system/config")
public class SyncInstanceConfigController extends BaseController
{
    @Autowired
    private ISyncInstanceConfigService syncInstanceConfigService;

    /**
     * 查询【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:config:list')")
    @GetMapping("/list")
    public TableDataInfo list(SyncInstanceConfig syncInstanceConfig)
    {
        startPage();
        List<SyncInstanceConfig> list = syncInstanceConfigService.selectSyncInstanceConfigList(syncInstanceConfig);
        return getDataTable(list);
    }

    /**
     * 导出【请填写功能名称】列表
     */
    @PreAuthorize("@ss.hasPermi('system:config:export')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SyncInstanceConfig syncInstanceConfig)
    {
        List<SyncInstanceConfig> list = syncInstanceConfigService.selectSyncInstanceConfigList(syncInstanceConfig);
        ExcelUtil<SyncInstanceConfig> util = new ExcelUtil<SyncInstanceConfig>(SyncInstanceConfig.class);
        return util.exportExcel(list, "config");
    }

    /**
     * 获取【请填写功能名称】详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:config:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(syncInstanceConfigService.selectSyncInstanceConfigById(id));
    }

    /**
     * 新增【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:config:add')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SyncInstanceConfig syncInstanceConfig)
    {
        return toAjax(syncInstanceConfigService.insertSyncInstanceConfig(syncInstanceConfig));
    }

    /**
     * 修改【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:config:edit')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SyncInstanceConfig syncInstanceConfig)
    {
        return toAjax(syncInstanceConfigService.updateSyncInstanceConfig(syncInstanceConfig));
    }

    /**
     * 删除【请填写功能名称】
     */
    @PreAuthorize("@ss.hasPermi('system:config:remove')")
    @Log(title = "【请填写功能名称】", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(syncInstanceConfigService.deleteSyncInstanceConfigByIds(ids));
    }
}
