package org.example.model;

public class InventoryItem {
    private int id;
    private String ingredientName;
    private int quantity;

    // Constructor
    public InventoryItem(int id, String ingredientName, int quantity) {
        this.id = id;
        this.ingredientName = ingredientName;
        this.quantity = quantity;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "InventoryItem{" +
                "id=" + id +
                ", ingredientName='" + ingredientName + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}

