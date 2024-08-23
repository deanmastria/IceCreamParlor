package org.example;

import org.example.model.InventoryItem;
import org.example.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {

    // Create (Add a new inventory item)
    public boolean addInventoryItem(String ingredientName, int quantity) {
        String sql = "INSERT INTO Inventory(ingredientName, quantity) VALUES(?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ingredientName);
            pstmt.setInt(2, quantity);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;  // Return true if the inventory item was successfully added

        } catch (SQLException e) {
            System.err.println("Error adding inventory item: " + e.getMessage());
            return false;
        }
    }

    // Read (Retrieve an inventory item by name)
    public InventoryItem getInventoryItemByName(String ingredientName) {
        String sql = "SELECT * FROM Inventory WHERE ingredientName = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, ingredientName);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new InventoryItem(
                        rs.getInt("id"),
                        rs.getString("ingredientName"),
                        rs.getInt("quantity")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving inventory item: " + e.getMessage());
        }
        return null;  // Return null if the inventory item is not found
    }

    // Read (Retrieve all inventory items)
    public List<InventoryItem> getAllInventoryItems() {
        String sql = "SELECT * FROM Inventory";
        List<InventoryItem> inventoryItems = new ArrayList<>();

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                inventoryItems.add(new InventoryItem(
                        rs.getInt("id"),
                        rs.getString("ingredientName"),
                        rs.getInt("quantity")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving all inventory items: " + e.getMessage());
        }
        return inventoryItems;  // Return list of inventory items
    }

    // Update (Update an inventory item's quantity)
    public boolean updateInventoryItemQuantity(int id, int quantity) {
        String sql = "UPDATE Inventory SET quantity = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, quantity);
            pstmt.setInt(2, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;  // Return true if the inventory item's quantity was successfully updated

        } catch (SQLException e) {
            System.err.println("Error updating inventory item quantity: " + e.getMessage());
            return false;
        }
    }

    // Delete (Remove an inventory item)
    public boolean deleteInventoryItem(int id) {
        String sql = "DELETE FROM Inventory WHERE id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;  // Return true if the inventory item was successfully deleted

        } catch (SQLException e) {
            System.err.println("Error deleting inventory item: " + e.getMessage());
            return false;
        }
    }
}
