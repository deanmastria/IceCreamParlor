package org.example;

import org.example.model.Table;
import org.example.utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TableDAO {

    // Method to retrieve all tables from the database
    public List<Table> getAllTables() {
        List<Table> tables = new ArrayList<>();
        String sql = "SELECT * FROM Tables";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                tables.add(new Table(
                        rs.getInt("id"),
                        rs.getInt("size"),
                        rs.getString("status")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving tables: " + e.getMessage());
        }

        return tables;
    }

    public boolean addTable(int size, String status) {
        String sql = "INSERT INTO Tables(size, status) VALUES(?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, size);
            pstmt.setString(2, status);

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error adding table: " + e.getMessage());
            return false;
        }
    }
    public boolean updateTableStatus(int id, String status) {
        String sql = "UPDATE Tables SET status = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, status);
            pstmt.setInt(2, id);

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error updating table status: " + e.getMessage());
            return false;
        }
    }
    public boolean deleteTable(int id) {
        String sql = "DELETE FROM Tables WHERE id = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);

            pstmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Error deleting table: " + e.getMessage());
            return false;
        }
    }
        // Method to assign an available table
        public Table assignAvailableTable() {
            String sql = "SELECT * FROM Tables WHERE status = 'Available' LIMIT 1";
            Table assignedTable = null;

            try (Connection conn = DatabaseConnection.connect();
                 PreparedStatement pstmt = conn.prepareStatement(sql);
                 ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    assignedTable = new Table(
                            rs.getInt("id"),
                            rs.getInt("size"),
                            rs.getString("status")
                    );
                    // Mark table as occupied
                    updateTableStatus(assignedTable.getId(), "Occupied");
                }

            } catch (SQLException e) {
                System.err.println("Error assigning table: " + e.getMessage());
            }

            return assignedTable;
        }
}
