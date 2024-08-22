package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.example.dao.SalesDAO;
import org.example.model.Sale;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SalesController {

    @FXML
    private TableView<Sale> salesTableView;

    @FXML
    private TableColumn<Sale, Integer> saleIdColumn;

    @FXML
    private TableColumn<Sale, Integer> orderIdColumn;

    @FXML
    private TableColumn<Sale, Double> revenueColumn;

    @FXML
    private TableColumn<Sale, String> dateColumn;

    @FXML
    private TextField orderIdField;

    @FXML
    private TextField revenueField;

    @FXML
    private Button addSaleButton;

    @FXML
    private Button deleteSaleButton;

    private final SalesDAO salesDAO = new SalesDAO();

    @FXML
    public void initialize() {
        // Initialize the columns with the model properties
        saleIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        orderIdColumn.setCellValueFactory(cellData -> cellData.getValue().orderIdProperty().asObject());
        revenueColumn.setCellValueFactory(cellData -> cellData.getValue().revenueProperty().asObject());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        // Load the sales data into the TableView
        loadSales();
    }

    private void loadSales() {
        ObservableList<Sale> sales = FXCollections.observableArrayList(salesDAO.getAllSales());
        salesTableView.setItems(sales);
    }

    @FXML
    private void handleAddSale() {
        try {
            int orderId = Integer.parseInt(orderIdField.getText());
            double revenue = Double.parseDouble(revenueField.getText());
            String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            boolean success = salesDAO.addSale(orderId, revenue, date);

            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Sale added successfully.");
                loadSales();  // Refresh the sales list
                clearInputFields();
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add sale.");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid input. Please enter valid numbers.");
        }
    }

    @FXML
    private void handleDeleteSale() {
        Sale selectedSale = salesTableView.getSelectionModel().getSelectedItem();
        if (selectedSale != null) {
            boolean success = salesDAO.deleteSale(selectedSale.getId());
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Sale deleted successfully.");
                loadSales();  // Refresh the sales list
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete sale.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a sale to delete.");
        }
    }

    private void clearInputFields() {
        orderIdField.clear();
        revenueField.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
