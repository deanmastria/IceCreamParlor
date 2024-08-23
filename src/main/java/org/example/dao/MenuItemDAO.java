package org.example.dao;

import org.example.model.MenuItem;
import org.example.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuItemDAO {

    // Create (Add a new menu item)
    public boolean addMenuItem(String name, String description, int preparationTime, double price, String ingredients, int categoryId) {
        String sql = "INSERT INTO MenuItems(name, description, preparationTime, price, ingredients, categoryId) VALUES(?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.setInt(3, preparationTime);
            pstmt.setDouble(4, price);
            pstmt.setString(5, ingredients);
            pstmt.setInt(6, categoryId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;  // Return true if the menu item was successfully added

        } catch (SQLException e) {
            System.err.println("Error adding menu item: " + e.getMessage());
            return false;
        }
    }

    // Read (Retrieve a menu item by name)
    public MenuItem getMenuItemByName(String name) {
        String sql = "SELECT * FROM MenuItems WHERE name = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new MenuItem(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("preparationTime"),
                        rs.getDouble("price"),
                        rs.getString("ingredients"),
                        rs.getInt("categoryId")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving menu item: " + e.getMessage());
        }
        return null;  // Return null if the menu item is not found
    }

    // Read (Retrieve all menu items by category)
    public List<MenuItem> getMenuItemsByCategory(int categoryId) {
        List<MenuItem> menuItems = new ArrayList<>();
        String sql = "SELECT * FROM MenuItems WHERE categoryId = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, categoryId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                menuItems.add(new MenuItem(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("preparationTime"),
                        rs.getDouble("price"),
                        rs.getString("ingredients"),
                        rs.getInt("categoryId")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving menu items by category: " + e.getMessage());
        }
        return menuItems;
    }




    // Read (Retrieve all menu items)
    public List<MenuItem> getAllMenuItems() {
        String sql = "SELECT * FROM MenuItems";
        List<MenuItem> menuItems = new ArrayList<>();

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                menuItems.add(new MenuItem(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("preparationTime"),
                        rs.getDouble("price"),
                        rs.getString("ingredients"),
                        rs.getInt("categoryId")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving all menu items: " + e.getMessage());
        }
        return menuItems;  // Return list of menu items
    }

    // Update (Update an existing menu item)
    public boolean updateMenuItem(int id, String name, String description, int preparationTime, double price, String ingredients, int categoryId) {
        String sql = "UPDATE MenuItems SET name = ?, description = ?, preparationTime = ?, price = ?, ingredients = ?, categoryId = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, description);
            pstmt.setInt(3, preparationTime);
            pstmt.setDouble(4, price);
            pstmt.setString(5, ingredients);
            pstmt.setInt(6, categoryId);
            pstmt.setInt(7, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;  // Return true if the menu item was successfully updated

        } catch (SQLException e) {
            System.err.println("Error updating menu item: " + e.getMessage());
            return false;
        }
    }

    // Delete (Delete a menu item)
    public boolean deleteMenuItem(int id) {
        String sql = "DELETE FROM MenuItems WHERE id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;  // Return true if the menu item was successfully deleted

        } catch (SQLException e) {
            System.err.println("Error deleting menu item: " + e.getMessage());
            return false;
        }
    }

    // Method to retrieve all categories from the database
    public List<String> getAllCategories() {
        List<String> categories = new ArrayList<>();
        String sql = "SELECT categoryName FROM Categories";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                categories.add(rs.getString("categoryName"));
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving categories: " + e.getMessage());
        }

        return categories;
    }

    // Method to get the category name by category ID
    public String getCategoryNameById(int categoryId) {
        String sql = "SELECT categoryName FROM Categories WHERE id = ?";
        String categoryName = null;

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, categoryId);  // Set the categoryId in the query
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                categoryName = rs.getString("categoryName");  // Retrieve the category name
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving category name: " + e.getMessage());
        }

        return categoryName;  // Return the category name or null if not found
    }

    // Convert category name to ID (assuming a method that retrieves category ID from the category name)
    // Method to get the category ID by category name (if needed)
    public int getCategoryID(String categoryName) {
        String sql = "SELECT id FROM Categories WHERE categoryName = ?";
        int categoryId = -1;

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, categoryName);  // Set the categoryName in the query
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                categoryId = rs.getInt("id");  // Retrieve the category ID
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving category ID: " + e.getMessage());
        }

        return categoryId;  // Return the category ID or -1 if not found
    }
}
