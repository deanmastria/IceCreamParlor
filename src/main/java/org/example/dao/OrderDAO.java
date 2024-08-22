package org.example.dao;

import org.example.model.Order;
import org.example.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    // Create (Place a new order)
    public boolean placeOrder(int userId, String items, double totalPrice, String status) {
        String sql = "INSERT INTO Orders(userId, items, totalPrice, status) VALUES(?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setString(2, items);
            pstmt.setDouble(3, totalPrice);
            pstmt.setString(4, status);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;  // Return true if the order was successfully placed

        } catch (SQLException e) {
            System.err.println("Error placing order: " + e.getMessage());
            return false;
        }
    }

    // Read (Retrieve an order by ID)
    public Order getOrderById(int id) {
        String sql = "SELECT * FROM Orders WHERE id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Order(
                        rs.getInt("id"),
                        rs.getInt("userId"),
                        rs.getString("items"),
                        rs.getDouble("totalPrice"),
                        rs.getString("status")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving order: " + e.getMessage());
        }
        return null;  // Return null if the order is not found
    }

    // Read (Retrieve all orders by status)
    public List<Order> getOrdersByStatus(String status) {
        String sql = "SELECT * FROM Orders WHERE status = ?";
        List<Order> orders = new ArrayList<>();

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                orders.add(new Order(
                        rs.getInt("id"),
                        rs.getInt("userId"),
                        rs.getString("items"),
                        rs.getDouble("totalPrice"),
                        rs.getString("status")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving orders by status: " + e.getMessage());
        }
        return orders;  // Return list of orders (could be empty if no orders found)
    }

    // Update (Update the status of an order)
    public boolean updateOrderStatus(int id, String status) {
        String sql = "UPDATE Orders SET status = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status);
            pstmt.setInt(2, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;  // Return true if the order status was successfully updated

        } catch (SQLException e) {
            System.err.println("Error updating order status: " + e.getMessage());
            return false;
        }
    }

    // Delete (Cancel an order)
    public boolean cancelOrder(int id) {
        String sql = "DELETE FROM Orders WHERE id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;  // Return true if the order was successfully canceled

        } catch (SQLException e) {
            System.err.println("Error canceling order: " + e.getMessage());
            return false;
        }
    }
}
