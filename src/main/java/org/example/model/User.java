package org.example.model;

public class User {
    private String username;
    private String passwordHash;
    private int roleId;

    // Constructor
    public User(String username, String passwordHash, int roleId) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.roleId = roleId;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public int getRoleId() {
        return roleId;
    }

    // Optionally add setters if needed
}


