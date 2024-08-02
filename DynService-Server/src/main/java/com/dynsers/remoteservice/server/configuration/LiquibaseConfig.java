package com.dynsers.remoteservice.server.configuration;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * Configuration class for liquibase.
 */
@Configuration
public class LiquibaseConfig {

    @Value("${spring.liquibase.change-log}")
    private String changeLogFile;

    /**
     * Creates a SpringLiquibase bean. SpringLiquibase is a Spring wrapper for Liquibase.
     *
     * @return a configured SpringLiquibase instance.
     */
    @Bean
    public SpringLiquibase liquibase() {
        var liquibase = new SpringLiquibase();
        liquibase.setChangeLog(changeLogFile);
        liquibase.setDataSource(this.liquibaseDataSource());
        return liquibase;
    }

    /**
     * Creates a DataSourceProperties bean for Liquibase. DataSourceProperties represents the configuration properties for a DataSource.
     *
     * @return a new DataSourceProperties instance.
     */
    @Bean
    @ConfigurationProperties("spring.datasource.liquibase")
    public DataSourceProperties liquibaseDataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * Creates a DataSource bean for Liquibase. DataSource is the interface that will be implemented to provide connections.
     *
     * @return a DataSource instance.
     */
    @Bean
    public DataSource liquibaseDataSource() {
        return liquibaseDataSourceProperties().initializeDataSourceBuilder().build();
    }
}