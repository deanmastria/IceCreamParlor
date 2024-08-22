package org.example.model;

public class Sale {
    private int id;
    private int orderId;
    private double revenue;
    private String date;

    // Constructor
    public Sale(int id, int orderId, double revenue, String date) {
        this.id = id;
        this.orderId = orderId;
        this.revenue = revenue;
        this.date = date;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public double getRevenue() {
        return revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", revenue=" + revenue +
                ", date='" + date + '\'' +
                '}';
    }
}
