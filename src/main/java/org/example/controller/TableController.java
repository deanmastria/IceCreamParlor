package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.example.dao.TableDAO;
import org.example.model.Table;

public class TableController {

    @FXML
    private TextField sizeTextField;

    @FXML
    private ComboBox<String> statusComboBox;

    @FXML
    private TableView<Table> tablesTableView;

    @FXML
    private TableColumn<Table, Integer> tableIdColumn;

    @FXML
    private TableColumn<Table, Integer> sizeColumn;

    @FXML
    private TableColumn<Table, String> statusColumn;

    private final TableDAO tableDAO = new TableDAO();

    @FXML
    public void initialize() {
        // Initialize table columns
        tableIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        sizeColumn.setCellValueFactory(cellData -> cellData.getValue().sizeProperty().asObject());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        // Initialize status combo box
        statusComboBox.setItems(FXCollections.observableArrayList("Available", "Reserved", "Occupied"));

        // Load tables into the table view
        loadTables();
    }

    private void loadTables() {
        ObservableList<Table> tables = FXCollections.observableArrayList(tableDAO.getAllTables());
        tablesTableView.setItems(tables);
    }

    @FXML
    private void handleAddTable() {
        try {
            int size = Integer.parseInt(sizeTextField.getText());
            String status = statusComboBox.getSelectionModel().getSelectedItem();

            boolean success = tableDAO.addTable(size, status);

            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Table added successfully.");
                clearForm();
                loadTables();  // Refresh table view
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add table.");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter a valid number for table size.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An unexpected error occurred.");
        }
    }

    @FXML
    private void handleUpdateTable() {
        Table selectedTable = tablesTableView.getSelectionModel().getSelectedItem();
        if (selectedTable != null) {
            try {
                int size = Integer.parseInt(sizeTextField.getText());
                String status = statusComboBox.getSelectionModel().getSelectedItem();

                boolean success = tableDAO.updateTableStatus(selectedTable.getId(), status);

                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Table updated successfully.");
                    clearForm();
                    loadTables();  // Refresh table view
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to update table.");
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter a valid number for table size.");
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", "An unexpected error occurred.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a table to update.");
        }
    }

    @FXML
    private void handleDeleteTable() {
        Table selectedTable = tablesTableView.getSelectionModel().getSelectedItem();
        if (selectedTable != null) {
            boolean success = tableDAO.deleteTable(selectedTable.getId());
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Table deleted successfully.");
                loadTables();  // Refresh table view
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete table.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a table to delete.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearForm() {
        sizeTextField.clear();
        statusComboBox.getSelectionModel().clearSelection();
    }
}
