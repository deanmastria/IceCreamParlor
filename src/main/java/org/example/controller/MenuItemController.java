package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.dao.MenuItemDAO;
import org.example.model.MenuItem;

public class MenuItemController {

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField descriptionTextField;

    @FXML
    private TextField preparationTimeTextField;

    @FXML
    private TextField priceTextField;

    @FXML
    private TextArea ingredientsTextArea;

    @FXML
    private ComboBox<String> categoryComboBox;

    @FXML
    private ComboBox<String> filterCategoryComboBox;

    @FXML
    private TableView<MenuItem> menuItemsTableView;

    @FXML
    private TableColumn<MenuItem, String> nameColumn;

    @FXML
    private TableColumn<MenuItem, String> descriptionColumn;

    @FXML
    private TableColumn<MenuItem, Integer> preparationTimeColumn;

    @FXML
    private TableColumn<MenuItem, Double> priceColumn;

    @FXML
    private TableColumn<MenuItem, String> ingredientsColumn;

    private final MenuItemDAO menuItemDAO = new MenuItemDAO();

    @FXML
    public void initialize() {
        // Initialize the table columns
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        descriptionColumn.setCellValueFactory(cellData -> cellData.getValue().descriptionProperty());
        preparationTimeColumn.setCellValueFactory(cellData -> cellData.getValue().preparationTimeProperty().asObject());
        priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        ingredientsColumn.setCellValueFactory(cellData -> cellData.getValue().ingredientsProperty());

        // Load menu items into the table view
        loadMenuItems();

        // Initialize the category combo box with example values (replace with actual categories from database)
        ObservableList<String> categories = FXCollections.observableArrayList(menuItemDAO.getAllCategories());
        categoryComboBox.setItems(categories);
        filterCategoryComboBox.setItems(categories);
    }

    private void loadMenuItems() {
        ObservableList<MenuItem> menuItems = FXCollections.observableArrayList(menuItemDAO.getAllMenuItems());
        menuItemsTableView.setItems(menuItems);
    }

    @FXML
    private void handleFilterByCategory() {
        String selectedCategory = filterCategoryComboBox.getSelectionModel().getSelectedItem();
        if (selectedCategory != null) {
            int categoryId = menuItemDAO.getCategoryID(selectedCategory); // Convert category name to ID
            ObservableList<MenuItem> filteredMenuItems = FXCollections.observableArrayList(menuItemDAO.getMenuItemsByCategory(categoryId));
            menuItemsTableView.setItems(filteredMenuItems);
        }
    }

    @FXML
    private void handleAddMenuItem() {
        try {
            String name = nameTextField.getText();
            String description = descriptionTextField.getText();
            int preparationTime = Integer.parseInt(preparationTimeTextField.getText());
            double price = Double.parseDouble(priceTextField.getText());
            String ingredients = ingredientsTextArea.getText();
            String selectedCategory = categoryComboBox.getSelectionModel().getSelectedItem();
            int categoryId = menuItemDAO.getCategoryID(selectedCategory); // Convert category name to ID

            boolean success = menuItemDAO.addMenuItem(name, description, preparationTime, price, ingredients, categoryId);

            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Menu item added successfully.");
                clearForm();
                loadMenuItems();  // Refresh table view
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to add menu item.");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter valid numbers for preparation time and price.");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An unexpected error occurred.");
        }
    }

    @FXML
    private void handleUpdateMenuItem() {
        MenuItem selectedItem = menuItemsTableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            try {
                String name = nameTextField.getText();
                String description = descriptionTextField.getText();
                int preparationTime = Integer.parseInt(preparationTimeTextField.getText());
                double price = Double.parseDouble(priceTextField.getText());
                String ingredients = ingredientsTextArea.getText();
                String selectedCategory = categoryComboBox.getSelectionModel().getSelectedItem();
                int categoryId = menuItemDAO.getCategoryID(selectedCategory); // Convert category name to ID

                boolean success = menuItemDAO.updateMenuItem(selectedItem.getId(), name, description, preparationTime, price, ingredients, categoryId);

                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Menu item updated successfully.");
                    clearForm();
                    loadMenuItems();  // Refresh table view
                } else {
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to update menu item.");
                }
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Please enter valid numbers for preparation time and price.");
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", "An unexpected error occurred.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a menu item to update.");
        }
    }

    @FXML
    private void handleDeleteMenuItem() {
        MenuItem selectedItem = menuItemsTableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            boolean success = menuItemDAO.deleteMenuItem(selectedItem.getId());
            if (success) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Menu item deleted successfully.");
                loadMenuItems();  // Refresh table view
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete menu item.");
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a menu item to delete.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearForm() {
        nameTextField.clear();
        descriptionTextField.clear();
        preparationTimeTextField.clear();
        priceTextField.clear();
        ingredientsTextArea.clear();
        categoryComboBox.getSelectionModel().clearSelection();
    }
}
