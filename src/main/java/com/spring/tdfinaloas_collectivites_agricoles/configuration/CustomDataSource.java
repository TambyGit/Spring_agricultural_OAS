package com.spring.tdfinaloas_collectivites_agricoles.configuration;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class CustomDataSource {
    private final Dotenv dotenv = Dotenv.load();
    private final String url = dotenv.get("URL");
    private final String user = dotenv.get("USER");
    private final String password = dotenv.get("PASSWORD");

    @Bean
    public DataSource getConnection() {
        try {
            DriverManagerDataSource dataSource = new DriverManagerDataSource();
            dataSource.setDriverClassName("org.postgresql.Driver");
            dataSource.setUrl(dotenv.get("URL"));
            dataSource.setUsername(dotenv.get("USER"));
            dataSource.setPassword(dotenv.get("PASSWORD"));

            return dataSource;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
