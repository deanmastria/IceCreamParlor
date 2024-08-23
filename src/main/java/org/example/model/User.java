package org.example.model;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private final int id;  // Make fields final for immutability
    private final String username;
    private final String passwordHash;
    private final int roleId;

    // Constructor with validation
    public User(int id, String username, String passwordHash, int roleId) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (passwordHash == null || passwordHash.isEmpty()) {
            throw new IllegalArgumentException("Password hash cannot be null or empty");
        }
        if (roleId <= 0) {
            throw new IllegalArgumentException("Role ID must be positive");
        }

        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.roleId = roleId;
    }

    // Getters only for immutability
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public int getRoleId() {
        return roleId;
    }

    // Override toString for better logging and debugging (excluding sensitive info)
    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "', roleId=" + roleId + "}";
    }

    // Override equals and hashCode for object comparison and usage in collections
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && roleId == user.roleId && Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, roleId);
    }

    // Optional: Implement a builder pattern for flexibility
    public static class Builder {
        private int id;
        private String username;
        private String passwordHash;
        private int roleId;

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPasswordHash(String passwordHash) {
            this.passwordHash = passwordHash;
            return this;
        }

        public Builder setRoleId(int roleId) {
            this.roleId = roleId;
            return this;
        }

        public User build() {
            return new User(id, username, passwordHash, roleId);
        }
    }
}
