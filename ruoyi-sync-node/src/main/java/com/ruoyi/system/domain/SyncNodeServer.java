package com.ruoyi.system.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 【请填写功能名称】对象 sync_node_server
 * 
 * @author ruoyi
 * @date 2020-12-13
 */
public class SyncNodeServer extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 集群id */
    @Excel(name = "集群id")
    private Long clusterId;

    /** 服务名 */
    @Excel(name = "服务名")
    private String name;

    /** 服务ip */
    @Excel(name = "服务ip")
    private String ip;

    /** master通讯端口 */
    @Excel(name = "master通讯端口")
    private Long adminPort;

    /** tcp端口 */
    @Excel(name = "tcp端口")
    private Long tcpPort;

    /** 状态 */
    @Excel(name = "状态")
    private String status;

    /** 修改时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "修改时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date modifiedTime;

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
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setIp(String ip) 
    {
        this.ip = ip;
    }

    public String getIp() 
    {
        return ip;
    }
    public void setAdminPort(Long adminPort) 
    {
        this.adminPort = adminPort;
    }

    public Long getAdminPort() 
    {
        return adminPort;
    }
    public void setTcpPort(Long tcpPort) 
    {
        this.tcpPort = tcpPort;
    }

    public Long getTcpPort() 
    {
        return tcpPort;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setModifiedTime(Date modifiedTime) 
    {
        this.modifiedTime = modifiedTime;
    }

    public Date getModifiedTime() 
    {
        return modifiedTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("clusterId", getClusterId())
            .append("name", getName())
            .append("ip", getIp())
            .append("adminPort", getAdminPort())
            .append("tcpPort", getTcpPort())
            .append("status", getStatus())
            .append("modifiedTime", getModifiedTime())
            .toString();
    }
}
