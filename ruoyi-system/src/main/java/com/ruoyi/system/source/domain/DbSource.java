package com.ruoyi.system.source.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 数据源对象 db_source
 * 
 * @author lizz
 * @date 2020-11-17
 */
public class DbSource extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private String id;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String name;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String dbHost;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String dbPort;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String dbUser;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String dbPwd;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private String kafkaUrl;

    /** $column.columnComment */
    @Excel(name = "${comment}", readConverterExp = "$column.readConverterExp()")
    private Integer dbServerId;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setDbHost(String dbHost) 
    {
        this.dbHost = dbHost;
    }

    public String getDbHost() 
    {
        return dbHost;
    }
    public void setDbPort(String dbPort) 
    {
        this.dbPort = dbPort;
    }

    public String getDbPort() 
    {
        return dbPort;
    }
    public void setDbUser(String dbUser) 
    {
        this.dbUser = dbUser;
    }

    public String getDbUser() 
    {
        return dbUser;
    }
    public void setDbPwd(String dbPwd) 
    {
        this.dbPwd = dbPwd;
    }

    public String getDbPwd() 
    {
        return dbPwd;
    }
    public void setKafkaUrl(String kafkaUrl) 
    {
        this.kafkaUrl = kafkaUrl;
    }

    public String getKafkaUrl() 
    {
        return kafkaUrl;
    }
    public void setDbServerId(Integer dbServerId) 
    {
        this.dbServerId = dbServerId;
    }

    public Integer getDbServerId() 
    {
        return dbServerId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("dbHost", getDbHost())
            .append("dbPort", getDbPort())
            .append("dbUser", getDbUser())
            .append("dbPwd", getDbPwd())
            .append("kafkaUrl", getKafkaUrl())
            .append("dbServerId", getDbServerId())
            .toString();
    }
}
