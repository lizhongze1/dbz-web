package com.ruoyi.system.source.controller;

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
import com.ruoyi.system.source.domain.DbSource;
import com.ruoyi.system.source.service.IDbSourceService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 数据源Controller
 * 
 * @author lizz
 * @date 2020-11-17
 */
@RestController
@RequestMapping("/system/source")
public class DbSourceController extends BaseController
{
    @Autowired
    private IDbSourceService dbSourceService;

    /**
     * 查询数据源列表
     */
    @PreAuthorize("@ss.hasPermi('system:source:list')")
    @GetMapping("/list")
    public TableDataInfo list(DbSource dbSource)
    {
        startPage();
        List<DbSource> list = dbSourceService.selectDbSourceList(dbSource);
        return getDataTable(list);
    }

    /**
     * 导出数据源列表
     */
    @PreAuthorize("@ss.hasPermi('system:source:export')")
    @Log(title = "数据源", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(DbSource dbSource)
    {
        List<DbSource> list = dbSourceService.selectDbSourceList(dbSource);
        ExcelUtil<DbSource> util = new ExcelUtil<DbSource>(DbSource.class);
        return util.exportExcel(list, "source");
    }

    /**
     * 获取数据源详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:source:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(dbSourceService.selectDbSourceById(id));
    }

    /**
     * 新增数据源
     */
    @PreAuthorize("@ss.hasPermi('system:source:add')")
    @Log(title = "数据源", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody DbSource dbSource)
    {
        return toAjax(dbSourceService.insertDbSource(dbSource));
    }

    /**
     * 修改数据源
     */
    @PreAuthorize("@ss.hasPermi('system:source:edit')")
    @Log(title = "数据源", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody DbSource dbSource)
    {
        return toAjax(dbSourceService.updateDbSource(dbSource));
    }

    /**
     * 删除数据源
     */
    @PreAuthorize("@ss.hasPermi('system:source:remove')")
    @Log(title = "数据源", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(dbSourceService.deleteDbSourceByIds(ids));
    }
}
