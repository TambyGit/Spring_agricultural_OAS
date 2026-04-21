package com.spring.tdfinaloas_collectivites_agricoles.configuration;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    public Connection getConnection() {
        Dotenv dotenv = Dotenv.load();
        String url = dotenv.get("URL");
        String user = dotenv.get("USER");
        String password = dotenv.get("PASSWORD");
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
