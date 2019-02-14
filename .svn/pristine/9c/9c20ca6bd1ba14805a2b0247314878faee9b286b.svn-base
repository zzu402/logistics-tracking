package com.htkfood.datasource;
import org.apache.ibatis.plugin.Interceptor;
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

import com.github.pagehelper.PageInterceptor;

import java.util.Properties;

import javax.sql.DataSource;
@Configuration
@MapperScan(basePackages = "com.htkfood.mapper.slave1", sqlSessionTemplateRef  = "slave1SqlSessionTemplate")
public class Slave1DataSourceConfig {
    @Bean(name = "slave1DataSource")
    @ConfigurationProperties(prefix = "spring.datasource.slave1")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().build();
    }
    @Bean(name = "slave1SqlSessionFactory")
    public SqlSessionFactory slaveSqlSessionFactory(@Qualifier("slave1DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:mybatis/mapper/slave1/*.xml"));
        
        Properties properties = new Properties();
//        properties.setProperty("helperDialect", "mysql");
        properties.setProperty("offsetAsPageNum", "true");
        properties.setProperty("rowBoundsWithCount", "true");
        properties.setProperty("reasonable", "true");
        Interceptor interceptor = new PageInterceptor();
        interceptor.setProperties(properties);
        bean.setPlugins(new Interceptor[] {interceptor});
        return bean.getObject();
    }
    @Bean(name = "slave1TransactionManager")
    public DataSourceTransactionManager slaveTransactionManager(@Qualifier("slave1DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
    @Bean(name = "slave1SqlSessionTemplate")
    public SqlSessionTemplate slaveSqlSessionTemplate(@Qualifier("slave1SqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}