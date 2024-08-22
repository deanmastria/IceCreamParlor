package org.example.controller;

import org.example.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    private User currentUser; // Declare currentUser

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

    public void setUser(User user) {
        this.currentUser = user;
        System.out.println("User: " + user);
        if (user != null) {
            System.out.println("Role ID: " + user.getRoleId());
            System.out.println("manageTablesButton: " + manageTablesButton);

            if (user.getRoleId() == getRoleIdByName("Staff")) {
                manageTablesButton.setVisible(true);
                manageOrdersButton.setVisible(true);
                manageMenuButton.setVisible(true);
                manageInventoryButton.setVisible(false);
                manageSalesButton.setVisible(false);
            } else if (user.getRoleId() == getRoleIdByName("Manager")) {
                manageTablesButton.setVisible(true);
                manageOrdersButton.setVisible(true);
                manageMenuButton.setVisible(true);
                manageInventoryButton.setVisible(true);
                manageSalesButton.setVisible(true);
            }
        }
    }

    private int getRoleIdByName(String roleName) {
        switch (roleName.toLowerCase()) {
            case "staff":
                return 1;
            case "manager":
                return 2;
            default:
                return -1;
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

            // Get the controller of the loaded FXML
            Object controller = loader.getController();

            // If the controller is an instance of MainController, pass the current user
            if (controller instanceof MainController) {
                MainController mainController = (MainController) controller;
                mainController.setUser(currentUser);
            }

            stage.show();

            // Optional: Close the current window if needed
            // Stage currentStage = (Stage) manageTablesButton.getScene().getWindow();
            // currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            // Optionally, show an error dialog
        }
    }

}
