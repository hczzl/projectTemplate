package com.glch.base.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
@Configuration
public class DruidConfig {
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.master")
    public DataSource masterDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "hiveDataSource")
    @Qualifier("hiveDataSource")
    @ConfigurationProperties("spring.datasource.hive.hive-master")
    public DataSource hiveDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "hiveTemplate")
    @Qualifier("hiveTemplate")
    public JdbcTemplate hiveTemplate(@Qualifier("hiveDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean(name = "storageDataSource")
    @Qualifier("storageDataSource")
    @ConfigurationProperties("spring.datasource.storage")
    public DataSource storageDataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "storageTemplate")
    @Qualifier("storageTemplate")
    public JdbcTemplate storageTemplate(@Qualifier("storageDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
