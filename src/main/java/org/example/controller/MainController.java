package org.example.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    public void handleSales() {
        loadScene("/path/to/sales.fxml", "Manage Sales");
    }

    public void handleInventory() {
        loadScene("/path/to/inventory.fxml", "Manage Inventory");
    }

    public void handleTables() {
        loadScene("/path/to/tables.fxml", "Manage Tables");
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

