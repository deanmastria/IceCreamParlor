package org.example.model;

import javafx.beans.property.*;

public class Table {
    private final IntegerProperty id;
    private final IntegerProperty size;
    private final StringProperty status;

    public Table(int id, int size, String status) {
        this.id = new SimpleIntegerProperty(id);
        this.size = new SimpleIntegerProperty(size);
        this.status = new SimpleStringProperty(status);
    }

    // Property methods
    public IntegerProperty idProperty() {
        return id;
    }

    public IntegerProperty sizeProperty() {
        return size;
    }

    public StringProperty statusProperty() {
        return status;
    }

    // Getters and Setters
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getSize() {
        return size.get();
    }

    public void setSize(int size) {
        this.size.set(size);
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }
}
