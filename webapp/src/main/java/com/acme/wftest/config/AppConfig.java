package com.acme.wftest.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
@ComponentScan(basePackages = "com.acme.wftest")
@MapperScan("com.acme.wftest.persistence")
public class AppConfig
{
  @Autowired
  DataSourceConfig dataSourceConfig;

  @Bean
  public JdbcTemplate jdbcTemplate()
  {
    return new JdbcTemplate(dataSourceConfig.dataSource());
  }

  @Bean
  public DataSourceTransactionManager transactionManager()
  {
    return new DataSourceTransactionManager(dataSourceConfig.dataSource());
  }

  @Bean
  public SqlSessionFactoryBean sqlSessionFactory() throws Exception
  {
    SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
    sessionFactory.setDataSource(dataSourceConfig.dataSource());

    return sessionFactory;
  }
}
