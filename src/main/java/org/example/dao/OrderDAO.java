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

    // Method to retrieve all orders from the database
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM Orders";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

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
            System.err.println("Error retrieving orders: " + e.getMessage());
        }

        return orders;
    }

    // Method to add a new order to the database
    public boolean addOrder(int userId, String items, double totalPrice, String status) {
        String sql = "INSERT INTO Orders(userId, items, totalPrice, status) VALUES(?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setString(2, items);
            pstmt.setDouble(3, totalPrice);
            pstmt.setString(4, status);

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error adding order: " + e.getMessage());
            return false;
        }
    }

    // Method to update the entire order
    public boolean updateOrder(int id, int userId, String items, double totalPrice, String status) {
        String sql = "UPDATE Orders SET userId = ?, items = ?, totalPrice = ?, status = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setString(2, items);
            pstmt.setDouble(3, totalPrice);
            pstmt.setString(4, status);
            pstmt.setInt(5, id);

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error updating order: " + e.getMessage());
            return false;
        }
    }

    // Method to delete (cancel) an order from the database
    public boolean deleteOrder(int id) {
        String sql = "DELETE FROM Orders WHERE id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error canceling order: " + e.getMessage());
            return false;
        }
    }

    // Method to retrieve an order by its ID
    public Order getOrderById(int id) {
        String sql = "SELECT * FROM Orders WHERE id = ?";
        Order order = null;

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                order = new Order(
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

        return order;
    }
}
