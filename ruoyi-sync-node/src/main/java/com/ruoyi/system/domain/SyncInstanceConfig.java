package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 sync_instance_config
 * 
 * @author ruoyi
 * @date 2020-12-13
 */
public class SyncInstanceConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 集群id */
    @Excel(name = "集群id")
    private Long clusterId;

    /** 服务id */
    @Excel(name = "服务id")
    private Long serverId;

    /** 实例名 */
    @Excel(name = "实例名")
    private String name;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 扩展配置 */
    @Excel(name = "扩展配置")
    private String content;

    /** 配置hash值 */
    @Excel(name = "配置hash值")
    private String contentMd5;

    /** 修改时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date modifiedTime;

    /** 数据源目标id */
    @Excel(name = "数据源目标id")
    private Long dsTargetId;

    /** 数据源源id */
    @Excel(name = "数据源源id")
    private Long dsSourceId;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setClusterId(Long clusterId) 
    {
        this.clusterId = clusterId;
    }

    public Long getClusterId() 
    {
        return clusterId;
    }
    public void setServerId(Long serverId) 
    {
        this.serverId = serverId;
    }

    public Long getServerId() 
    {
        return serverId;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setContentMd5(String contentMd5) 
    {
        this.contentMd5 = contentMd5;
    }

    public String getContentMd5() 
    {
        return contentMd5;
    }
    public void setModifiedTime(Date modifiedTime) 
    {
        this.modifiedTime = modifiedTime;
    }

    public Date getModifiedTime() 
    {
        return modifiedTime;
    }
    public void setDsTargetId(Long dsTargetId) 
    {
        this.dsTargetId = dsTargetId;
    }

    public Long getDsTargetId() 
    {
        return dsTargetId;
    }
    public void setDsSourceId(Long dsSourceId) 
    {
        this.dsSourceId = dsSourceId;
    }

    public Long getDsSourceId() 
    {
        return dsSourceId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("clusterId", getClusterId())
            .append("serverId", getServerId())
            .append("name", getName())
            .append("status", getStatus())
            .append("content", getContent())
            .append("contentMd5", getContentMd5())
            .append("modifiedTime", getModifiedTime())
            .append("dsTargetId", getDsTargetId())
            .append("dsSourceId", getDsSourceId())
            .toString();
    }
}
