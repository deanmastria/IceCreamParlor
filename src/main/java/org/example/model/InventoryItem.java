package org.example.model;

import javafx.beans.property.*;

public class InventoryItem {
    private final IntegerProperty id;
    private final StringProperty ingredientName;
    private final IntegerProperty quantity;

    public InventoryItem(int id, String ingredientName, int quantity) {
        this.id = new SimpleIntegerProperty(id);
        this.ingredientName = new SimpleStringProperty(ingredientName);
        this.quantity = new SimpleIntegerProperty(quantity);
    }

    // Property methods
    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty ingredientNameProperty() {
        return ingredientName;
    }

    public IntegerProperty quantityProperty() {
        return quantity;
    }

    // Getters and Setters
    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getIngredientName() {
        return ingredientName.get();
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName.set(ingredientName);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }
}
