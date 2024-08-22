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

    // Method to add a new sale record to the database
    public boolean addSale(int orderId, double revenue, String date) {
        String sql = "INSERT INTO Sales(orderId, revenue, date) VALUES(?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, orderId);
            pstmt.setDouble(2, revenue);
            pstmt.setString(3, date);

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error adding sale: " + e.getMessage());
            return false;
        }
    }

    // Method to retrieve all sales records from the database
    public List<Sale> getAllSales() {
        List<Sale> sales = new ArrayList<>();
        String sql = "SELECT * FROM Sales";

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
            System.err.println("Error retrieving sales: " + e.getMessage());
        }

        return sales;
    }

    // Method to delete a sale record from the database
    public boolean deleteSale(int id) {
        String sql = "DELETE FROM Sales WHERE id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error deleting sale: " + e.getMessage());
            return false;
        }
    }

    // Method to retrieve sales for a specific date
    public List<Sale> getSalesByDate(String date) {
        List<Sale> sales = new ArrayList<>();
        String sql = "SELECT * FROM Sales WHERE date = ?";

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

        return sales;
    }

    // Additional methods to generate sales reports can be added here...
}
