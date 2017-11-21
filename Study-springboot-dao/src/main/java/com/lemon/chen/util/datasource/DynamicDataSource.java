package com.lemon.chen.util.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Created by chenhualong on 2016/11/24.
 * 获取当前线程的对应数据源
 */
public class DynamicDataSource extends AbstractRoutingDataSource {


    @Override
    protected Object determineCurrentLookupKey() {

        return DynamicDataSourceContextHolder.getDataSourceType();
    }
}
