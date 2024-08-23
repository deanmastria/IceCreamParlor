package org.example.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

public class DataSeeder {

    public static void seedRoles(Connection conn) {
        String[] roles = {"staff", "manager"};
        String sqlInsert = "INSERT INTO Roles(roleName) VALUES(?)";
        String sqlCheck = "SELECT COUNT(*) FROM Roles WHERE roleName = ?";

        try (PreparedStatement pstmtInsert = conn.prepareStatement(sqlInsert);
             PreparedStatement pstmtCheck = conn.prepareStatement(sqlCheck)) {

            for (String role : roles) {
                pstmtCheck.setString(1, role);
                ResultSet rs = pstmtCheck.executeQuery();
                rs.next();
                int count = rs.getInt(1);

                if (count == 0) {
                    pstmtInsert.setString(1, role);
                    pstmtInsert.executeUpdate();
                    System.out.println("Role '" + role + "' has been seeded.");
                } else {
                    System.out.println("Role '" + role + "' already exists.");
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to seed roles data: " + e.getMessage());
        }
    }

    public static void seedUsers(Connection conn) {
        String sql = "INSERT INTO Users(username, passwordHash, roleId) VALUES(?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Add staff user
            String staffPasswordHash = hashPassword("staff123");
            pstmt.setString(1, "staff");
            pstmt.setString(2, staffPasswordHash);
            pstmt.setInt(3, getRoleId(conn, "staff"));
            pstmt.executeUpdate();
            System.out.println("Seeded staff password hash: " + staffPasswordHash);

            // Add manager user
            String managerPasswordHash = hashPassword("manager123");
            pstmt.setString(1, "manager");
            pstmt.setString(2, managerPasswordHash);
            pstmt.setInt(3, getRoleId(conn, "manager"));
            pstmt.executeUpdate();
            System.out.println("Seeded manager password hash: " + managerPasswordHash);

            System.out.println("Users data has been seeded.");
        } catch (Exception e) {
            System.err.println("Failed to seed users data: " + e.getMessage());
        }
    }

    private static int getRoleId(Connection conn, String roleName) {
        String sql = "SELECT id FROM Roles WHERE roleName = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, roleName);
            var rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (Exception e) {
            System.err.println("Failed to retrieve role ID for " + roleName + ": " + e.getMessage());
        }
        return -1; // Return an invalid ID if not found
    }

    private static String hashPassword(String password) {
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

    public static void seedCategories(Connection conn) {
        String[] categories = {"Ice Cream", "Toppings", "Drinks"};
        String sql = "INSERT INTO Categories(categoryName) VALUES(?)";  // Correct column name

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (String category : categories) {
                pstmt.setString(1, category);
                pstmt.executeUpdate();
            }
            System.out.println("Categories data has been seeded.");
        } catch (Exception e) {
            System.err.println("Failed to seed categories data: " + e.getMessage());
        }
    }

    public static void seedTables(Connection conn) {
        String[] tableTypes = {"Booth", "Round Table", "Outside Table"};
        int[] tableSizes = {4, 6, 4};  // Booth: 4 seats, Round Table: 6 seats, Outside Table: 4 seats
        int[] tableQuantities = {4, 4, 3};  // Booth: 4 tables, Round Table: 4 tables, Outside Table: 3 tables

        String sql = "INSERT INTO Tables(size, status) VALUES(?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            for (int i = 0; i < tableTypes.length; i++) {
                for (int j = 0; j < tableQuantities[i]; j++) {
                    pstmt.setInt(1, tableSizes[i]);
                    pstmt.setString(2, "Available");  // Default status to 'Available'
                    pstmt.executeUpdate();
                }
            }
            System.out.println("Tables data has been seeded.");
        } catch (Exception e) {
            System.err.println("Failed to seed tables data: " + e.getMessage());
        }
    }

    public static void seedInventory(Connection conn) {
        String[] iceCreamFlavors = {
                "Vanilla", "Chocolate", "Strawberry", "Mint Chocolate Chip",
                "Cookies and Cream", "Rocky Road", "Butter Pecan", "Coffee",
                "Pistachio", "Mango", "Caramel Swirl", "Coconut",
                "Peanut Butter Cup", "Banana", "Rum Raisin", "Blueberry"
        };

        String[] toppings = {
                "Sprinkles", "Chocolate Chips", "Whipped Cream", "Cherries",
                "Caramel Sauce", "Hot Fudge", "Nuts", "Marshmallows",
                "Oreos", "Gummy Bears"
        };

        String[] sorbetFlavors = {"Lemon", "Raspberry", "Mango", "Pineapple"};
        String[] lemonadeFlavors = {"Classic Lemonade", "Strawberry Lemonade", "Peach Lemonade"};
        String[] sodaFlavors = {"Cola", "Orange Soda", "Root Beer", "Ginger Ale", "Lemon-Lime Soda"};
        String milk = "Milk";  // For milkshakes

        String sql = "INSERT INTO Inventory(ingredientName, quantity) VALUES(?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            // Insert ice cream flavors
            insertItems(pstmt, iceCreamFlavors, " Ice Cream", 100);

            // Insert toppings
            insertItems(pstmt, toppings, "", 15);

            // Insert sorbet flavors
            insertItems(pstmt, sorbetFlavors, " Sorbet", 70);

            // Insert lemonade flavors
            insertItems(pstmt, lemonadeFlavors, "", 50);

            // Insert soda flavors
            insertItems(pstmt, sodaFlavors, "", 50);

            // Insert milk for milkshakes
            pstmt.setString(1, milk);
            pstmt.setInt(2, 200);  // Set initial quantity
            pstmt.executeUpdate();

            System.out.println("Inventory data has been seeded.");
        } catch (Exception e) {
            System.err.println("Failed to seed inventory data: " + e.getMessage());
        }
    }

    private static void insertItems(PreparedStatement pstmt, String[] items, String suffix, int quantity) throws Exception {
        for (String item : items) {
            pstmt.setString(1, item + suffix);
            pstmt.setInt(2, quantity);  // Set initial quantity
            pstmt.executeUpdate();
        }
    }

    public static void seedMenuItems(Connection conn) {
        // Seed ice cream menu items
        seedIceCreamMenuItems(conn);

        // Seed toppings menu items
        seedToppingMenuItems(conn);

        // Seed sorbet menu items
        seedSorbetMenuItems(conn);

        // Seed lemonade menu items
        seedLemonadeMenuItems(conn);

        // Seed soda menu items
        seedSodaMenuItems(conn);
    }

    public static void seedIceCreamMenuItems(Connection conn) {
        String[] iceCreamFlavors = {
                "Vanilla", "Chocolate", "Strawberry", "Mint Chocolate Chip",
                "Cookies and Cream", "Rocky Road", "Butter Pecan", "Coffee",
                "Pistachio", "Mango", "Caramel Swirl", "Coconut",
                "Peanut Butter Cup", "Banana", "Rum Raisin", "Blueberry"
        };

        try {
            String sql = "INSERT INTO MenuItems(name, description, preparationTime, price, ingredients, categoryId) VALUES(?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (String flavor : iceCreamFlavors) {
                    pstmt.setString(1, flavor + " Ice Cream");
                    pstmt.setString(2, "Delicious " + flavor + " ice cream.");
                    pstmt.setInt(3, 5); // Preparation time in minutes
                    pstmt.setDouble(4, 2.99); // Price in dollars
                    pstmt.setString(5, flavor + ", Milk, Sugar"); // Example ingredients
                    pstmt.setInt(6, 1); // Category ID for Ice Cream
                    pstmt.executeUpdate();
                }
            }
            System.out.println("Ice cream menu items seeded.");
        } catch (Exception e) {
            System.err.println("Failed to seed ice cream menu items: " + e.getMessage());
        }
    }

    public static void seedToppingMenuItems(Connection conn) {
        String[] toppings = {
                "Sprinkles", "Chocolate Chips", "Whipped Cream", "Cherries",
                "Caramel Sauce", "Hot Fudge", "Nuts", "Marshmallows",
                "Oreos", "Gummy Bears"
        };

        try {
            String sql = "INSERT INTO MenuItems(name, description, preparationTime, price, ingredients, categoryId) VALUES(?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (String topping : toppings) {
                    pstmt.setString(1, topping);
                    pstmt.setString(2, "Tasty " + topping + " topping.");
                    pstmt.setInt(3, 2); // Preparation time in minutes
                    pstmt.setDouble(4, 0.99); // Price in dollars
                    pstmt.setString(5, topping + ", Sugar"); // Example ingredients
                    pstmt.setInt(6, 2); // Category ID for Toppings
                    pstmt.executeUpdate();
                }
            }
            System.out.println("Topping menu items seeded.");
        } catch (Exception e) {
            System.err.println("Failed to seed topping menu items: " + e.getMessage());
        }
    }

    public static void seedSorbetMenuItems(Connection conn) {
        String[] sorbetFlavors = {"Lemon", "Raspberry", "Mango", "Pineapple"};

        try {
            String sql = "INSERT INTO MenuItems(name, description, preparationTime, price, ingredients, categoryId) VALUES(?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (String sorbet : sorbetFlavors) {
                    pstmt.setString(1, sorbet + " Sorbet");
                    pstmt.setString(2, "Refreshing " + sorbet + " sorbet.");
                    pstmt.setInt(3, 5); // Preparation time in minutes
                    pstmt.setDouble(4, 3.99); // Price in dollars
                    pstmt.setString(5, sorbet + ", Water, Sugar"); // Example ingredients
                    pstmt.setInt(6, 1); // Category ID for Sorbet (assuming Ice Cream)
                    pstmt.executeUpdate();
                }
            }
            System.out.println("Sorbet menu items seeded.");
        } catch (Exception e) {
            System.err.println("Failed to seed sorbet menu items: " + e.getMessage());
        }
    }

    public static void seedLemonadeMenuItems(Connection conn) {
        String[] lemonadeFlavors = {"Classic Lemonade", "Strawberry Lemonade", "Peach Lemonade"};

        try {
            String sql = "INSERT INTO MenuItems(name, description, preparationTime, price, ingredients, categoryId) VALUES(?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (String lemonade : lemonadeFlavors) {
                    pstmt.setString(1, lemonade);
                    pstmt.setString(2, lemonade + " flavored lemonade.");
                    pstmt.setInt(3, 3); // Preparation time in minutes
                    pstmt.setDouble(4, 1.99); // Price in dollars
                    pstmt.setString(5, lemonade + ", Water, Sugar, Lemon"); // Example ingredients
                    pstmt.setInt(6, 3); // Category ID for Drinks
                    pstmt.executeUpdate();
                }
            }
            System.out.println("Lemonade menu items seeded.");
        } catch (Exception e) {
            System.err.println("Failed to seed lemonade menu items: " + e.getMessage());
        }
    }

    public static void seedSodaMenuItems(Connection conn) {
        String[] sodaFlavors = {"Cola", "Orange Soda", "Root Beer", "Ginger Ale", "Lemon-Lime Soda"};

        try {
            String sql = "INSERT INTO MenuItems(name, description, preparationTime, price, ingredients, categoryId) VALUES(?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                for (String soda : sodaFlavors) {
                    pstmt.setString(1, soda);
                    pstmt.setString(2, soda + " flavored soda.");
                    pstmt.setInt(3, 2); // Preparation time in minutes
                    pstmt.setDouble(4, 1.49); // Price in dollars
                    pstmt.setString(5, soda + ", Water, Sugar, Flavoring"); // Example ingredients
                    pstmt.setInt(6, 3); // Category ID for Drinks
                    pstmt.executeUpdate();
                }
            }
            System.out.println("Soda menu items seeded.");
        } catch (Exception e) {
            System.err.println("Failed to seed soda menu items: " + e.getMessage());
        }
    }

    public static void seedCustomers(Connection conn) {
        String sql = "INSERT INTO Customers(name, email) VALUES(?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, "John Doe");
            pstmt.setString(2, "john@example.com");
            pstmt.executeUpdate();

            pstmt.setString(1, "Jane Doe");
            pstmt.setString(2, "jane@example.com");
            pstmt.executeUpdate();

            System.out.println("Customers data has been seeded.");
        } catch (SQLException e) {
            System.err.println("Failed to seed customers data: " + e.getMessage());
        }
    }
}
