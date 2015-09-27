package com.acme.wftest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration
@Profile("developer")
public class DeveloperDataSourceConfig implements DataSourceConfig
{
  @Bean
  public DataSource dataSource() {
    return new EmbeddedDatabaseBuilder()
      .setType(EmbeddedDatabaseType.HSQL)
      .addScript("classpath:sample-schema.sql")
      .addScript("classpath:demo-data.sql")
      .build();
  }
}
