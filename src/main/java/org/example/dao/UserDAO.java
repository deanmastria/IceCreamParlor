package org.example.dao;

import org.example.model.User;
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

    // Read (Retrieve user details for login)
    public User getUserByUsername(String username) {
        String sql = "SELECT * FROM Users WHERE username = ?";

        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String passwordHash = rs.getString("passwordHash");
                int roleId = rs.getInt("roleId");
                return new User(username, passwordHash, roleId);  // Return a User object with the retrieved details
            }

        } catch (SQLException e) {
            System.err.println("Error retrieving user: " + e.getMessage());
        }
        return null;  // Return null if user not found
    }

    // Verify password during login
    public boolean verifyPassword(User user, String password) {
        String enteredHash = hashPassword(password);

        // Debugging: Print the hashes for comparison
        System.out.println("Entered password hash: " + enteredHash);
        System.out.println("Stored password hash: " + user.getPasswordHash());

        return user.getPasswordHash().equals(enteredHash);
    }

    // Hash password using SHA-256
    private String hashPassword(String password) {
        try {
            var md = java.security.MessageDigest.getInstance("SHA-256");
            var bytes = md.digest(password.getBytes());
            var sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Failed to hash password: " + e.getMessage(), e);
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

    // Login method
    public boolean loginUser(String username, String password) {
        User user = getUserByUsername(username);
        if (user != null) {
            return verifyPassword(user, password);
        } else {
            System.out.println("User not found with username: " + username);
            return false;
        }
    }
}

