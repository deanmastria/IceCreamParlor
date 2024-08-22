package org.example.dao;

import org.example.model.Table;
import org.example.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TableDAO {

    // Create (Add a new table)
    public boolean addTable(int size, String status) {
        String sql = "INSERT INTO Tables(size, status) VALUES(?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, size);
            pstmt.setString(2, status);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;  // Return true if the table was successfully added

        } catch (SQLException e) {
            System.err.println("Error adding table: " + e.getMessage());
            return false;
        }
    }

    // Read (Retrieve a table by ID)
    public Table getTableById(int id) {
        String sql = "SELECT * FROM Tables WHERE id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Table(
                        rs.getInt("id"),
                        rs.getInt("size"),
                        rs.getString("status")
                );
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving table: " + e.getMessage());
        }
        return null;  // Return null if the table is not found
    }

    // Read (Retrieve all tables by status)
    public List<Table> getTablesByStatus(String status) {
        String sql = "SELECT * FROM Tables WHERE status = ?";
        List<Table> tables = new ArrayList<>();

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                tables.add(new Table(
                        rs.getInt("id"),
                        rs.getInt("size"),
                        rs.getString("status")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving tables by status: " + e.getMessage());
        }
        return tables;  // Return list of tables (could be empty if no tables found)
    }

    // Update (Update table status)
    public boolean updateTableStatus(int id, String status) {
        String sql = "UPDATE Tables SET status = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status);
            pstmt.setInt(2, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;  // Return true if the table status was successfully updated

        } catch (SQLException e) {
            System.err.println("Error updating table status: " + e.getMessage());
            return false;
        }
    }

    // Delete (Remove a table)
    public boolean deleteTable(int id) {
        String sql = "DELETE FROM Tables WHERE id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;  // Return true if the table was successfully deleted

        } catch (SQLException e) {
            System.err.println("Error deleting table: " + e.getMessage());
            return false;
        }
    }
}
