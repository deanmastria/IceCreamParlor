package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.dao.SalesDAO;
import org.example.model.Sale;

import java.io.FileWriter;
import java.io.IOException;

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

    private final SalesDAO salesDAO = new SalesDAO();

    @FXML
    public void initialize() {
        // Initialize table columns
        saleIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        orderIdColumn.setCellValueFactory(cellData -> cellData.getValue().orderIdProperty().asObject());
        revenueColumn.setCellValueFactory(cellData -> cellData.getValue().revenueProperty().asObject());
        dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());

        // Load sales data into the table view
        loadSales();
    }

    private void loadSales() {
        ObservableList<Sale> sales = FXCollections.observableArrayList(salesDAO.getAllSales());
        salesTableView.setItems(sales);
    }

    @FXML
    private void handleGenerateSalesReport() {
        try {
            // Fetch sales data from the table view
            ObservableList<Sale> salesData = salesTableView.getItems();

            // Define the report file path and name
            FileWriter writer = new FileWriter("Sales_Report.csv");

            // Write CSV header
            writer.append("Sale ID,Order ID,Revenue,Date\n");

            // Write sales data
            for (Sale sale : salesData) {
                writer.append(sale.getId() + ",")
                        .append(sale.getOrderId() + ",")
                        .append(sale.getRevenue() + ",")
                        .append(sale.getDate() + "\n");
            }

            writer.flush();
            writer.close();

            showAlert(Alert.AlertType.INFORMATION, "Success", "Sales report generated successfully.");

        } catch (IOException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to generate sales report.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}