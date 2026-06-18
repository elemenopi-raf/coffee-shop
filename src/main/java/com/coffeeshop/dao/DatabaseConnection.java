package com.coffeeshop.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

<<<<<<< Updated upstream
public class DatabaseConnection {
    private static final HikariDataSource dataSource;
=======
/**
 * Provides database connections using JDBC with configuration from
 * db.properties.
 */
public final class DatabaseConnection {

    /** The database URL. */
    private static String url;

    /** The database user. */
    private static String user;

    /** The database password. */
    private static String password;
>>>>>>> Stashed changes

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
     * Returns a new database connection.
     *
     * @return a connection to the database
     * @throws Exception if a database error occurs
     */
    public static Connection getConnection() throws Exception {
        return dataSource.getConnection();
    }

    // Private constructor to prevent instantiation
    private DatabaseConnection() {
    }
}
