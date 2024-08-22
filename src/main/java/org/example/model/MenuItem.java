package org.example.model;

import javafx.beans.property.*;

public class MenuItem {
    private final IntegerProperty id;
    private final StringProperty name;
    private final StringProperty description;
    private final IntegerProperty preparationTime;
    private final DoubleProperty price;
    private final StringProperty ingredients;
    private final IntegerProperty quantity; // Extra integer parameter

    // Updated constructor with the extra integer parameter
    public MenuItem(int id, String name, String description, int preparationTime, double price, String ingredients, int quantity) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.description = new SimpleStringProperty(description);
        this.preparationTime = new SimpleIntegerProperty(preparationTime);
        this.price = new SimpleDoubleProperty(price);
        this.ingredients = new SimpleStringProperty(ingredients);
        this.quantity = new SimpleIntegerProperty(quantity);  // Initialize the extra parameter
    }

    // Property methods
    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public IntegerProperty preparationTimeProperty() {
        return preparationTime;
    }

    public DoubleProperty priceProperty() {
        return price;
    }

    public StringProperty ingredientsProperty() {
        return ingredients;
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

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public int getPreparationTime() {
        return preparationTime.get();
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime.set(preparationTime);
    }

    public double getPrice() {
        return price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }

    public String getIngredients() {
        return ingredients.get();
    }

    public void setIngredients(String ingredients) {
        this.ingredients.set(ingredients);
    }

    public int getQuantity() {
        return quantity.get();
    }

    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }
}
