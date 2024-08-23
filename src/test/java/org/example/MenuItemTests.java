package org.example;


import org.example.model.MenuItem;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.DoubleProperty;


class MenuItemTests {

    private MenuItem menuItem;

    @BeforeEach
    void setUp() {
        menuItem = new MenuItem(1, "Test Item", "Test Description", 15, 9.99, "Test Ingredients", 10);
    }

    @Test
    void testIdProperty() {
        IntegerProperty idProperty = menuItem.idProperty();
        assertNotNull(idProperty, "idProperty should not be null");
    }

    @Test
    void testNameProperty() {
        StringProperty nameProperty = menuItem.nameProperty();
        assertNotNull(nameProperty, "nameProperty should not be null");
    }

    @Test
    void testDescriptionProperty() {
        StringProperty descriptionProperty = menuItem.descriptionProperty();
        assertNotNull(descriptionProperty, "descriptionProperty should not be null");
    }

    @Test
    void testPreparationTimeProperty() {
        IntegerProperty preparationTimeProperty = menuItem.preparationTimeProperty();
        assertNotNull(preparationTimeProperty, "preparationTimeProperty should not be null");
    }

    @Test
    void testPriceProperty() {
        DoubleProperty priceProperty = menuItem.priceProperty();
        assertNotNull(priceProperty, "priceProperty should not be null");
    }

    @Test
    void testIngredientsProperty() {
        StringProperty ingredientsProperty = menuItem.ingredientsProperty();
        assertNotNull(ingredientsProperty, "ingredientsProperty should not be null");
    }

    @Test
    void testQuantityProperty() {
        IntegerProperty quantityProperty = menuItem.quantityProperty();
        assertNotNull(quantityProperty, "quantityProperty should not be null");
    }

    @Test
    void testGetAndSetId() {
        int testId = 5;
        menuItem.setId(testId);
        assertEquals(testId, menuItem.getId(), "getId should return the correct id");
    }

    @Test
    void testGetAndSetName() {
        String testName = "Test Name";
        menuItem.setName(testName);
        assertEquals(testName, menuItem.getName(), "getName should return the correct name");
    }

    @Test
    void testGetAndSetDescription() {
        String testDescription = "Test Description";
        menuItem.setDescription(testDescription);
        assertEquals(testDescription, menuItem.getDescription(), "getDescription should return the correct description");
    }

    @Test
    void testGetAndSetPreparationTime() {
        int testPreparationTime = 30;
        menuItem.setPreparationTime(testPreparationTime);
        assertEquals(testPreparationTime, menuItem.getPreparationTime(), "getPreparationTime should return the correct preparation time");
    }

    @Test
    void testGetAndSetPrice() {
        double testPrice = 9.99;
        menuItem.setPrice(testPrice);
        assertEquals(testPrice, menuItem.getPrice(), "getPrice should return the correct price");
    }
}
