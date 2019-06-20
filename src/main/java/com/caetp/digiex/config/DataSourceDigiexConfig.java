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
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * Created by wangzy on 2019/2/16.
 */
@Configuration
@MapperScan(basePackages = "com.caetp.digiex.entity.mapper", sqlSessionTemplateRef  = "digiexSqlSessionTemplate")
public class DataSourceDigiexConfig {

    @Bean(name = "digiexDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.digiex")
    @Primary
    public DataSource digiexDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean(name = "digiexSqlSessionFactory")
    @Primary
    public SqlSessionFactory digiexSqlSessionFactory(@Qualifier("digiexDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mapper/digiex/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "digiexTransactionManager")
    @Primary
    public DataSourceTransactionManager digiexTransactionManager(@Qualifier("digiexDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "digiexSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate digiexSqlSessionTemplate(@Qualifier("digiexSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
