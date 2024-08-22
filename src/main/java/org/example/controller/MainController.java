package org.example.controller;

import org.example.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private Button manageTablesButton;

    @FXML
    private Button manageOrdersButton;

    @FXML
    private Button manageMenuButton;

    @FXML
    private Button manageInventoryButton;

    @FXML
    private Button manageSalesButton;

    private User currentUser;

    public void setUser(User user) {
        this.currentUser = user;
        if (user.getRoleId() == getRoleIdByName("Staff")) {
            // Hide or disable certain buttons for staff
            manageTablesButton.setVisible(true);
            manageOrdersButton.setVisible(true);
            manageMenuButton.setVisible(true);
            manageInventoryButton.setVisible(false);
            manageSalesButton.setVisible(false);
        } else if (user.getRoleId() == getRoleIdByName("Manager")) {
            // Manager has access to everything
            manageTablesButton.setVisible(true);
            manageOrdersButton.setVisible(true);
            manageMenuButton.setVisible(true);
            manageInventoryButton.setVisible(true);
            manageSalesButton.setVisible(true);
        }
    }

    // Helper method to retrieve role ID by role name
    private int getRoleIdByName(String roleName) {
        // This should return the appropriate role ID based on the roleName.
        // You would typically retrieve this from your database or a predefined set of constants.
        switch (roleName.toLowerCase()) {
            case "staff":
                return 1;  // Assuming role ID 1 is for Staff
            case "manager":
                return 2;  // Assuming role ID 2 is for Manager
            default:
                return -1;  // Unknown role
        }
    }

    public void handleSales() {
        loadScene("/fxml/sales.fxml", "Manage Sales");
    }

    public void handleInventory() {
        loadScene("/fxml/inventory.fxml", "Manage Inventory");
    }

    public void handleTables() {
        loadScene("/fxml/table.fxml", "Manage Tables");
    }

    public void handleMenu() {
        loadScene("/fxml/menu.fxml", "Manage Menu");
    }

    public void handleOrders() {
        loadScene("/fxml/order.fxml", "Manage Orders");
    }

    private void loadScene(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}