package org.example.model;

import javafx.beans.property.*;

public class Order {
    private final IntegerProperty id;  // This is your order ID
    private final IntegerProperty userId;
    private final StringProperty items;
    private final DoubleProperty totalPrice;
    private final StringProperty status;

    public Order(int id, int userId, String items, double totalPrice, String status) {
        this.id = new SimpleIntegerProperty(id);
        this.userId = new SimpleIntegerProperty(userId);
        this.items = new SimpleStringProperty(items);
        this.totalPrice = new SimpleDoubleProperty(totalPrice);
        this.status = new SimpleStringProperty(status);
    }

    // Property methods
    public IntegerProperty idProperty() {
        return id;
    }

    public IntegerProperty userIdProperty() {
        return userId;
    }

    public StringProperty itemsProperty() {
        return items;
    }

    public DoubleProperty totalPriceProperty() {
        return totalPrice;
    }

    public StringProperty statusProperty() {
        return status;
    }

    // Getter for ID
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getUserId() {
        return userId.get();
    }

    public void setUserId(int userId) {
        this.userId.set(userId);
    }

    public String getItems() {
        return items.get();
    }

    public void setItems(String items) {
        this.items.set(items);
    }

    public double getTotalPrice() {
        return totalPrice.get();
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice.set(totalPrice);
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }
}
