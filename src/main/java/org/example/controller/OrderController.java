package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.example.dao.OrderDAO;
import org.example.model.Order;

public class OrderController {

    @FXML
    private TableView<Order> ordersTableView;

    @FXML
    private TableColumn<Order, Integer> orderIdColumn;

    @FXML
    private TableColumn<Order, Integer> userIdColumn;

    @FXML
    private TableColumn<Order, String> itemsColumn;

    @FXML
    private TableColumn<Order, Double> totalPriceColumn;

    @FXML
    private TableColumn<Order, String> statusColumn;

    @FXML
    private Button updateOrderStatusButton;

    @FXML
    private Button cancelOrderButton;

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
    private void handleUpdateOrderStatus() {
        Order selectedOrder = ordersTableView.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            boolean success = orderDAO.updateOrderStatus(selectedOrder.getId(), "Preparing");  // Example status update
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Order status updated.");
                loadOrders();  // Refresh orders
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to update order status.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an order to update.");
        }
    }

    @FXML
    private void handleCancelOrder() {
        Order selectedOrder = ordersTableView.getSelectionModel().getSelectedItem();
        if (selectedOrder != null) {
            boolean success = orderDAO.cancelOrder(selectedOrder.getId());
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Order canceled successfully.");
                loadOrders();  // Refresh orders
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to cancel order.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select an order to cancel.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
