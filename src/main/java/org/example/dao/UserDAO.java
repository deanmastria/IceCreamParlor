package org.example.dao;

import org.example.utils.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    // Create (Register a new user)
    public boolean registerUser(String username, String passwordHash, int roleId) {
        String sql = "INSERT INTO Users(username, passwordHash, roleId) VALUES(?, ?, ?)";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, passwordHash);
            pstmt.setInt(3, roleId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;  // Return true if the user was successfully registered

        } catch (SQLException e) {
            System.err.println("Error registering user: " + e.getMessage());
            return false;
        }
    }

    // Read (Login a user)
    public boolean loginUser(String username, String passwordHash) {
        String sql = "SELECT * FROM Users WHERE username = ? AND passwordHash = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, passwordHash);

            ResultSet rs = pstmt.executeQuery();
            return rs.next();  // Return true if a user with the provided credentials is found

        } catch (SQLException e) {
            System.err.println("Error logging in user: " + e.getMessage());
            return false;
        }
    }

    // Update (Update user profile)
    public boolean updateUser(String username, String newPasswordHash, int newRoleId) {
        String sql = "UPDATE Users SET passwordHash = ?, roleId = ? WHERE username = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newPasswordHash);
            pstmt.setInt(2, newRoleId);
            pstmt.setString(3, username);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;  // Return true if the user was successfully updated

        } catch (SQLException e) {
            System.err.println("Error updating user: " + e.getMessage());
            return false;
        }
    }

    // Delete (Delete a user)
    public boolean deleteUser(String username) {
        String sql = "DELETE FROM Users WHERE username = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;  // Return true if the user was successfully deleted

        } catch (SQLException e) {
            System.err.println("Error deleting user: " + e.getMessage());
            return false;
        }
    }
}
