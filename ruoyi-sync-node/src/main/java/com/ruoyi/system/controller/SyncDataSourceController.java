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
import com.ruoyi.system.domain.SyncDataSource;
import com.ruoyi.system.service.ISyncDataSourceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 数据源维护Controller
 * 
 * @author lizz
 * @date 2020-12-13
 */
@RestController
@RequestMapping("/system/source")
public class SyncDataSourceController extends BaseController
{
    @Autowired
    private ISyncDataSourceService syncDataSourceService;

    /**
     * 查询数据源维护列表
     */
    @PreAuthorize("@ss.hasPermi('system:source:list')")
    @GetMapping("/list")
    public TableDataInfo list(SyncDataSource syncDataSource)
    {
        startPage();
        List<SyncDataSource> list = syncDataSourceService.selectSyncDataSourceList(syncDataSource);
        return getDataTable(list);
    }

    /**
     * 导出数据源维护列表
     */
    @PreAuthorize("@ss.hasPermi('system:source:export')")
    @Log(title = "数据源维护", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(SyncDataSource syncDataSource)
    {
        List<SyncDataSource> list = syncDataSourceService.selectSyncDataSourceList(syncDataSource);
        ExcelUtil<SyncDataSource> util = new ExcelUtil<SyncDataSource>(SyncDataSource.class);
        return util.exportExcel(list, "source");
    }

    /**
     * 获取数据源维护详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:source:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(syncDataSourceService.selectSyncDataSourceById(id));
    }

    /**
     * 新增数据源维护
     */
    @PreAuthorize("@ss.hasPermi('system:source:add')")
    @Log(title = "数据源维护", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SyncDataSource syncDataSource)
    {
        return toAjax(syncDataSourceService.insertSyncDataSource(syncDataSource));
    }

    /**
     * 修改数据源维护
     */
    @PreAuthorize("@ss.hasPermi('system:source:edit')")
    @Log(title = "数据源维护", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SyncDataSource syncDataSource)
    {
        return toAjax(syncDataSourceService.updateSyncDataSource(syncDataSource));
    }

    /**
     * 删除数据源维护
     */
    @PreAuthorize("@ss.hasPermi('system:source:remove')")
    @Log(title = "数据源维护", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(syncDataSourceService.deleteSyncDataSourceByIds(ids));
    }
}
