package com.caetp.digiex.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created by wangzy on 2019/2/16.
 */
@Configuration
@MapperScan(basePackages = "com.caetp.digiex.entity.rmsmapper", sqlSessionTemplateRef  = "digiexrmsSqlSessionTemplate")
public class DataSourceDigiexrmsConfig {

    @Bean(name = "digiexrmsDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.digiexrms")
    public DataSource digiexrmsDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "digiexrmsSqlSessionFactory")
    public SqlSessionFactory digiexrmsSqlSessionFactory(@Qualifier("digiexrmsDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/digiexrms/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "digiexrmsTransactionManager")
    public DataSourceTransactionManager digiexrmsTransactionManager(@Qualifier("digiexrmsDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "digiexrmsSqlSessionTemplate")
    public SqlSessionTemplate digiexrmsSqlSessionTemplate(@Qualifier("digiexrmsSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
