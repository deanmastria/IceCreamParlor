package org.example.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

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
