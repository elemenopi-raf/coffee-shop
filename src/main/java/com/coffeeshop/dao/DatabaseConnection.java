package com.coffeeshop.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * Provides database connections using HikariCP connection pool.
 * Configuration is loaded from db.properties on the classpath.
 */
public final class DatabaseConnection {

    /** The HikariCP connection pool. */
    private static final HikariDataSource dataSource;

    static {
        try (InputStream input = DatabaseConnection.class.getClassLoader()
                .getResourceAsStream("db.properties")) {
            Properties props = new Properties();
            props.load(input);
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(props.getProperty("db.url"));
            config.setUsername(props.getProperty("db.user"));
            config.setPassword(props.getProperty("db.password"));
            config.setDriverClassName("org.postgresql.Driver");
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(2);
            dataSource = new HikariDataSource(config);
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize database pool", e);
        }
    }

    /**
     * Returns a connection from the pool.
     *
     * @return a database connection
     * @throws Exception if a connection cannot be obtained
     */
    public static Connection getConnection() throws Exception {
        return dataSource.getConnection();
    }

    private DatabaseConnection() {
    }
}
