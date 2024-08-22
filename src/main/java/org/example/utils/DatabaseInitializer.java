package org.example.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseInitializer {

    private static final Logger LOGGER = Logger.getLogger(DatabaseInitializer.class.getName());

    public static void initialize() {
        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement()) {

            // Create necessary tables
            createUsersTable(stmt);
            createMenuItemsTable(stmt);
            createOrdersTable(stmt);
            createTablesTable(stmt);
            createInventoryTable(stmt);
            createSalesTable(stmt);

            LOGGER.info("Database has been initialized.");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error during database initialization", e);
        }
    }

    private static void createUsersTable(Statement stmt) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT NOT NULL UNIQUE," +
                "passwordHash TEXT NOT NULL," +
                "role TEXT NOT NULL)";
        stmt.execute(sql);
        LOGGER.info("Users table created successfully.");
    }

    private static void createMenuItemsTable(Statement stmt) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS MenuItems (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "description TEXT," +
                "preparationTime INTEGER," +
                "price REAL NOT NULL," +
                "ingredients TEXT)";
        stmt.execute(sql);
        LOGGER.info("MenuItems table created successfully.");
    }

    private static void createOrdersTable(Statement stmt) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Orders (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "userId INTEGER NOT NULL," +
                "items TEXT NOT NULL," +
                "totalPrice REAL NOT NULL," +
                "status TEXT NOT NULL," +
                "FOREIGN KEY (userId) REFERENCES Users(id))";
        stmt.execute(sql);
        LOGGER.info("Orders table created successfully.");
    }

    private static void createTablesTable(Statement stmt) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Tables (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "size INTEGER NOT NULL," +
                "status TEXT NOT NULL)";
        stmt.execute(sql);
        LOGGER.info("Tables table created successfully.");
    }

    private static void createInventoryTable(Statement stmt) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Inventory (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ingredientName TEXT NOT NULL," +
                "quantity INTEGER NOT NULL)";
        stmt.execute(sql);
        LOGGER.info("Inventory table created successfully.");
    }

    private static void createSalesTable(Statement stmt) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Sales (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "orderId INTEGER NOT NULL," +
                "revenue REAL NOT NULL," +
                "date TEXT NOT NULL," +
                "FOREIGN KEY (orderId) REFERENCES Orders(id))";
        stmt.execute(sql);
        LOGGER.info("Sales table created successfully.");
    }
}
