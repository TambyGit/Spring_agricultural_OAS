package com.spring.tdfinaloas_collectivites_agricoles_finale.configuration;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class CustomDataSource {
    private final Dotenv dotenv = Dotenv.load();

    @Bean
    public DataSource getConnection() {
        try {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("org.postgresql.Driver");
            dataSource.setUrl(dotenv.get("DB_URL"));
            dataSource.setUsername(dotenv.get("DB_USER"));
            dataSource.setPassword(dotenv.get("DB_PASSWORD"));

            return dataSource;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
