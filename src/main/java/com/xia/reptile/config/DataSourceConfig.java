package com.xia.reptile.config;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfig {

	@Primary
	@Bean(name = "reptileDataSource")
	@Qualifier("reptileDataSource")
	@ConfigurationProperties(prefix = "spring.datasource.reptile")
	public DataSource diagnosisDataSource() throws SQLException {
		DataSource dataSource = DataSourceBuilder.create().build();
		dataSource.setLoginTimeout(600);
		return dataSource;
	}

}
