package com.tr.file.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

/**
 * Created by chenhualong on 2017/7/21.
 *
 * 因为必须要使用tk.mybatis.spring.mapper.MapperScannerConfigurer 注入才能使用通用mapper.
 *
 * 所以重新注入configuer才能使用,这个类建议放到config里面比较合适。
 *
 */

@Configuration
public class MapperScan {


    /**
     * 重新注入通用mapper的数据资源
     * @return
     */
    @Bean
    public MapperScannerConfigurer getMapper() {

        MapperScannerConfigurer configurer = new MapperScannerConfigurer();

        configurer.setBasePackage(DruidTrFileDataSourceConfig.PACKAGE);

        configurer.setSqlSessionFactoryBeanName("fileSqlSessionFactory");

        return configurer;

    }


}
