package org.example.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseInitializer {
    private static final Logger logger = Logger.getLogger(DatabaseInitializer.class.getName());

    public static void initialize() {
        Connection conn = null;
        try {
            conn = DatabaseConnection.connect();
            conn.setAutoCommit(false); // Start transaction
            try (Statement stmt = conn.createStatement()) {

                // Create necessary tables
                TableCreation.createRolesTable(stmt);
                TableCreation.createUsersTable(stmt);
                TableCreation.createCategoriesTable(stmt);
                TableCreation.createMenuItemsTable(stmt);
                TableCreation.createOrdersTable(stmt);
                TableCreation.createTablesTable(stmt);
                TableCreation.createInventoryTable(stmt);
                TableCreation.createSalesTable(stmt);
                TableCreation.createMenuItemIngredientsTable(stmt);

                // Seed data if not already seeded
                if (!isTableDataSeeded(conn, "Roles")) {
                    DataSeeder.seedRoles(conn);
                }
                if (!isTableDataSeeded(conn, "Categories")) {
                    DataSeeder.seedCategories(conn);
                }
                if (!isTableDataSeeded(conn, "Tables")) {
                    DataSeeder.seedTables(conn);
                }
                if (!isTableDataSeeded(conn, "Inventory")) {
                    DataSeeder.seedInventory(conn);
                }
                if (!isTableDataSeeded(conn, "MenuItems")) {
                    DataSeeder.seedMenuItems(conn);
                }
                if (!isTableDataSeeded(conn, "MenuItemIngredients")) {
                    DataSeeder.seedMenuItemIngredients(conn);
                }

                conn.commit(); // Commit transaction
                logger.info("Database has been initialized.");
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error during database initialization. Rolling back changes.", e);
            try {
                if (conn != null) {
                    conn.rollback(); // Rollback transaction on error
                }
            } catch (SQLException rollbackEx) {
                logger.log(Level.SEVERE, "Failed to rollback transaction.", rollbackEx);
            }
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true); // Ensure autocommit is re-enabled
                    conn.close(); // Close connection
                }
            } catch (SQLException closeEx) {
                logger.log(Level.SEVERE, "Failed to close connection.", closeEx);
            }
        }
    }

    private static boolean isTableDataSeeded(Connection conn, String tableName) {
        String sql = "SELECT COUNT(*) FROM " + tableName;
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            return rs.next() && rs.getInt(1) > 0;  // If count > 0, data is already seeded
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error checking data in " + tableName, e);
        }
        return false;
    }
}
