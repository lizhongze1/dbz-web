package com.ruoyi.datasource.support;

public interface AdapterConfig {
    String getDataSourceKey();

    AdapterMapping getMapping();

    interface AdapterMapping {
        String getEtlCondition();
    }
}
