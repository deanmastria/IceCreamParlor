package org.example.utils;

import java.sql.SQLException;
import java.sql.Statement;

public class TableCreation {

    public static void createRolesTable(Statement stmt) {
        String sql = "CREATE TABLE IF NOT EXISTS Roles (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "roleName TEXT NOT NULL UNIQUE)";
        try {
            stmt.execute(sql);
            System.out.println("Roles table created successfully.");
        } catch (SQLException e) {
            System.err.println("Error creating Roles table: " + e.getMessage());
        }
    }

    public static void createCategoriesTable(Statement stmt) {
        String sql = "CREATE TABLE IF NOT EXISTS Categories (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "categoryName TEXT NOT NULL UNIQUE)";
        try {
            stmt.execute(sql);
            System.out.println("Categories table created successfully.");
        } catch (SQLException e) {
            System.err.println("Error creating Categories table: " + e.getMessage());
        }
    }

    public static void createUsersTable(Statement stmt) {
        String sql = "CREATE TABLE IF NOT EXISTS Users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT NOT NULL UNIQUE," +
                "passwordHash TEXT NOT NULL," +
                "roleId INTEGER," +
                "FOREIGN KEY (roleId) REFERENCES Roles(id))";
        try {
            stmt.execute(sql);
            System.out.println("Users table created successfully.");
        } catch (SQLException e) {
            System.err.println("Error creating Users table: " + e.getMessage());
        }
    }

    public static void createMenuItemsTable(Statement stmt) {
        String sql = "CREATE TABLE IF NOT EXISTS MenuItems (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "description TEXT," +
                "preparationTime INTEGER CHECK(preparationTime > 0)," +
                "price REAL NOT NULL CHECK(price >= 0)," +
                "ingredients TEXT," +
                "categoryId INTEGER," +
                "FOREIGN KEY (categoryId) REFERENCES Categories(id))";
        try {
            stmt.execute(sql);
            System.out.println("MenuItems table created successfully.");
        } catch (SQLException e) {
            System.err.println("Error creating MenuItems table: " + e.getMessage());
        }
    }

    public static void createOrdersTable(Statement stmt) {
        String sql = "CREATE TABLE IF NOT EXISTS Orders (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "userId INTEGER NOT NULL," +
                "customerId INTEGER," +
                "items TEXT NOT NULL," +
                "totalPrice REAL NOT NULL CHECK(totalPrice >= 0)," +
                "status TEXT NOT NULL CHECK(status IN ('waiting', 'preparing', 'ready'))," +
                "FOREIGN KEY (userId) REFERENCES Users(id)," +
                "FOREIGN KEY (customerId) REFERENCES Customers(customerId))";
        try {
            stmt.execute(sql);
            System.out.println("Orders table created successfully.");
        } catch (SQLException e) {
            System.err.println("Error creating Orders table: " + e.getMessage());
        }
    }



    public static void createTablesTable(Statement stmt) {
        String sql = "CREATE TABLE IF NOT EXISTS Tables (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "size INTEGER NOT NULL CHECK(size > 0)," +
                "status TEXT NOT NULL CHECK(status IN ('available', 'reserved', 'occupied')))";
        try {
            stmt.execute(sql);
            System.out.println("Tables table created successfully.");
        } catch (SQLException e) {
            System.err.println("Error creating Tables table: " + e.getMessage());
        }
    }

    public static void createInventoryTable(Statement stmt) {
        String sql = "CREATE TABLE IF NOT EXISTS Inventory (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ingredientName TEXT NOT NULL," +
                "quantity INTEGER NOT NULL CHECK(quantity >= 0))";
        try {
            stmt.execute(sql);
            System.out.println("Inventory table created successfully.");
        } catch (SQLException e) {
            System.err.println("Error creating Inventory table: " + e.getMessage());
        }
    }

    public static void createSalesTable(Statement stmt) {
        String sql = "CREATE TABLE IF NOT EXISTS Sales (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "orderId INTEGER NOT NULL," +
                "revenue REAL NOT NULL CHECK(revenue >= 0)," +
                "date TEXT NOT NULL," +
                "FOREIGN KEY (orderId) REFERENCES Orders(id))";
        try {
            stmt.execute(sql);
            System.out.println("Sales table created successfully.");
        } catch (SQLException e) {
            System.err.println("Error creating Sales table: " + e.getMessage());
        }
    }

    public static void createMenuItemIngredientsTable(Statement stmt) {
        String sql = "CREATE TABLE IF NOT EXISTS MenuItemIngredients (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "menuItemId INTEGER," +
                "ingredientId INTEGER," +
                "quantityNeeded INTEGER NOT NULL," +
                "FOREIGN KEY (menuItemId) REFERENCES MenuItems(id)," +
                "FOREIGN KEY (ingredientId) REFERENCES Inventory(id))";
        try {
            stmt.execute(sql);
            System.out.println("MenuItemIngredients table created successfully.");
        } catch (SQLException e) {
            System.err.println("Error creating MenuItemIngredients table: " + e.getMessage());
        }
    }

    public static void createCustomersTable(Statement stmt) {
        String sql = "CREATE TABLE IF NOT EXISTS Customers (" +
                "customerId INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT NOT NULL," +
                "email TEXT)";
        try {
            stmt.execute(sql);
            System.out.println("Customers table created successfully.");
        } catch (SQLException e) {
            System.err.println("Error creating Customers table: " + e.getMessage());
        }
    }



}
