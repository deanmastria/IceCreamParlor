package org.example.model;

import javafx.beans.property.*;

public class Sale {
    private final IntegerProperty id;
    private final IntegerProperty orderId;
    private final DoubleProperty revenue;
    private final StringProperty date;

    public Sale(int id, int orderId, double revenue, String date) {
        this.id = new SimpleIntegerProperty(id);
        this.orderId = new SimpleIntegerProperty(orderId);
        this.revenue = new SimpleDoubleProperty(revenue);
        this.date = new SimpleStringProperty(date);
    }

    // Property methods
    public IntegerProperty idProperty() {
        return id;
    }

    public IntegerProperty orderIdProperty() {
        return orderId;
    }

    public DoubleProperty revenueProperty() {
        return revenue;
    }

    public StringProperty dateProperty() {
        return date;
    }

    // Getters and Setters
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getOrderId() {
        return orderId.get();
    }

    public void setOrderId(int orderId) {
        this.orderId.set(orderId);
    }

    public double getRevenue() {
        return revenue.get();
    }

    public void setRevenue(double revenue) {
        this.revenue.set(revenue);
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }
}
