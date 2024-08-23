package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.example.InventoryDAO;
import org.example.model.InventoryItem;

public class InventoryController {

    @FXML
    private TextField ingredientNameTextField;

    @FXML
    private TextField quantityTextField;

    @FXML
    private TableView<InventoryItem> inventoryTableView;

    @FXML
    private TableColumn<InventoryItem, Integer> ingredientIdColumn;

    @FXML
    private TableColumn<InventoryItem, String> ingredientNameColumn;

    @FXML
    private TableColumn<InventoryItem, Integer> quantityColumn;

    private final InventoryDAO inventoryDAO = new InventoryDAO();

    @FXML
    public void initialize() {
        // Initialize table columns
        ingredientIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        ingredientNameColumn.setCellValueFactory(cellData -> cellData.getValue().ingredientNameProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());

        // Load inventory items into the table view
        loadInventoryItems();
    }

    private void loadInventoryItems() {
        ObservableList<InventoryItem> inventoryItems = FXCollections.observableArrayList(inventoryDAO.getAllInventoryItems());
        inventoryTableView.setItems(inventoryItems);
    }

    @FXML
    private void handleAddInventoryItem() {
        try {
            String ingredientName = ingredientNameTextField.getText();
            int quantity = Integer.parseInt(quantityTextField.getText());

            boolean success = inventoryDAO.addInventoryItem(ingredientName, quantity);

            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Inventory item added successfully.");
                clearForm();
                loadInventoryItems();  // Refresh table view
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add inventory item.");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter a valid number for quantity.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An unexpected error occurred.");
        }
    }

    @FXML
    private void handleUpdateInventoryItem() {
        InventoryItem selectedItem = inventoryTableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                int quantity = Integer.parseInt(quantityTextField.getText());

                boolean success = inventoryDAO.updateInventoryItemQuantity(selectedItem.getId(), quantity);

                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Inventory item updated successfully.");
                    clearForm();
                    loadInventoryItems();  // Refresh table view
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to update inventory item.");
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter a valid number for quantity.");
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", "An unexpected error occurred.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an inventory item to update.");
        }
    }

    @FXML
    private void handleDeleteInventoryItem() {
        InventoryItem selectedItem = inventoryTableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            boolean success = inventoryDAO.deleteInventoryItem(selectedItem.getId());
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Inventory item deleted successfully.");
                loadInventoryItems();  // Refresh table view
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete inventory item.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an inventory item to delete.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearForm() {
        ingredientNameTextField.clear();
        quantityTextField.clear();
    }
}
