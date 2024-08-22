package org.example.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlite:restaurant.db";  // Path to your SQLite DB file
    private static final Logger logger = Logger.getLogger(DatabaseConnection.class.getName());

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL);
            if (conn != null) {
                logger.info("Connected to SQLite database.");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Failed to connect to SQLite database.", e);
        }
        return conn;
    }
}
