package org.example.dao;

import org.example.model.Sale;
import org.example.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SalesDAO {

    // Create (Record a sale)
    public boolean recordSale(int orderId, double revenue, String date) {
        String sql = "INSERT INTO Sales(orderId, revenue, date) VALUES(?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderId);
            pstmt.setDouble(2, revenue);
            pstmt.setString(3, date);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;  // Return true if the sale was successfully recorded

        } catch (SQLException e) {
            System.err.println("Error recording sale: " + e.getMessage());
            return false;
        }
    }

    // Read (Retrieve a sale by ID)
    public Sale getSaleById(int id) {
        String sql = "SELECT * FROM Sales WHERE id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Sale(
                        rs.getInt("id"),
                        rs.getInt("orderId"),
                        rs.getDouble("revenue"),
                        rs.getString("date")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving sale: " + e.getMessage());
        }
        return null;  // Return null if the sale is not found
    }

    // Read (Retrieve all sales by date)
    public List<Sale> getSalesByDate(String date) {
        String sql = "SELECT * FROM Sales WHERE date = ?";
        List<Sale> sales = new ArrayList<>();

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, date);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                sales.add(new Sale(
                        rs.getInt("id"),
                        rs.getInt("orderId"),
                        rs.getDouble("revenue"),
                        rs.getString("date")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving sales by date: " + e.getMessage());
        }
        return sales;  // Return list of sales (could be empty if no sales found)
    }

    // Read (Retrieve all sales)
    public List<Sale> getAllSales() {
        String sql = "SELECT * FROM Sales";
        List<Sale> sales = new ArrayList<>();

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                sales.add(new Sale(
                        rs.getInt("id"),
                        rs.getInt("orderId"),
                        rs.getDouble("revenue"),
                        rs.getString("date")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving all sales: " + e.getMessage());
        }
        return sales;  // Return list of sales
    }

    // Delete (Delete a sale)
    public boolean deleteSale(int id) {
        String sql = "DELETE FROM Sales WHERE id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;  // Return true if the sale was successfully deleted

        } catch (SQLException e) {
            System.err.println("Error deleting sale: " + e.getMessage());
            return false;
        }
    }
}
