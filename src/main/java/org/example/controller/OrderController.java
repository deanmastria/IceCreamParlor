package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.example.dao.OrderDAO;
import org.example.model.Order;

public class OrderController {

    @FXML
    private TableView<Order> ordersTableView;  // This is synchronized with the FXML

    @FXML
    private TableColumn<Order, Integer> orderIdColumn;  // This is synchronized with the FXML

    @FXML
    private TableColumn<Order, Integer> userIdColumn;  // This is synchronized with the FXML

    @FXML
    private TableColumn<Order, String> itemsColumn;  // This is synchronized with the FXML

    @FXML
    private TableColumn<Order, Double> totalPriceColumn;  // This is synchronized with the FXML

    @FXML
    private TableColumn<Order, String> statusColumn;  // This is synchronized with the FXML

    @FXML
    private TextField userIdField;  // TextField for User ID input

    @FXML
    private TextField itemsField;  // TextField for Items input

    @FXML
    private TextField totalPriceField;  // TextField for Total Price input

    @FXML
    private TextField statusField;  // TextField for Status input

    private final OrderDAO orderDAO = new OrderDAO();

    @FXML
    public void initialize() {
        // Initialize table columns
        orderIdColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        userIdColumn.setCellValueFactory(cellData -> cellData.getValue().userIdProperty().asObject());
        itemsColumn.setCellValueFactory(cellData -> cellData.getValue().itemsProperty());
        totalPriceColumn.setCellValueFactory(cellData -> cellData.getValue().totalPriceProperty().asObject());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());

        // Load orders into the table view
        loadOrders();
    }

    private void loadOrders() {
        ObservableList<Order> orders = FXCollections.observableArrayList(orderDAO.getAllOrders());
        ordersTableView.setItems(orders);
    }

    @FXML
    private void handleAddOrder() {
        try {
            int userId = Integer.parseInt(userIdField.getText());
            String items = itemsField.getText();
            double totalPrice = Double.parseDouble(totalPriceField.getText());
            String status = statusField.getText();

            boolean success = orderDAO.addOrder(userId, items, totalPrice, status);

            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Order added successfully.");
                loadOrders();  // Refresh table view
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add order.");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An unexpected error occurred.");
        }
    }

    @FXML
    private void handleUpdateOrder() {
        Order selectedOrder = ordersTableView.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            try {
                String items = itemsField.getText();
                double totalPrice = Double.parseDouble(totalPriceField.getText());
                String status = statusField.getText();

                boolean success = orderDAO.updateOrder(selectedOrder.getId(), selectedOrder.getUserId(), items, totalPrice, status);

                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Order updated successfully.");
                    loadOrders();  // Refresh table view
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to update order.");
                }
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", "An unexpected error occurred.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an order to update.");
        }
    }

    @FXML
    private void handleDeleteOrder() {
        Order selectedOrder = ordersTableView.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            boolean success = orderDAO.deleteOrder(selectedOrder.getId());
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Order deleted successfully.");
                loadOrders();  // Refresh table view
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete order.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an order to delete.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
