package org.example.dao;

import org.example.model.MenuItem;
import org.example.utils.DatabaseConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuItemDAOTest {

    private MenuItemDAO menuItemDAO;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() throws SQLException {
        // Create the spies and mocks
        menuItemDAO = spy(new MenuItemDAO());
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        // Mock the connection setup
        doReturn(mockConnection).when(DatabaseConnection.class, "connect");  // Mock static method if needed
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
    }

    @Test
    void addMenuItem() throws SQLException {
        // Arrange
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);  // Simulate successful execution

        // Act
        boolean result = menuItemDAO.addMenuItem("Ice Cream", "Delicious vanilla", 10, 3.99, "Milk, Sugar, Vanilla", 1);

        // Assert
        assertTrue(result, "Menu item should be added successfully.");
        verify(mockPreparedStatement).setString(1, "Ice Cream");  // Verifying that the correct SQL parameters are being set
    }

    @Test
    void getMenuItemsByCategory() throws SQLException {
        // Arrange
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true).thenReturn(false); // Simulate one result
        when(mockResultSet.getString("name")).thenReturn("Ice Cream");

        // Act
        List<MenuItem> result = menuItemDAO.getMenuItemsByCategory(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Ice Cream", result.get(0).getName());
    }

    @Test
    void getAllMenuItems() throws SQLException {
        // Arrange
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false); // Simulate two rows
        when(mockResultSet.getInt("id")).thenReturn(1).thenReturn(2);
        when(mockResultSet.getString("name")).thenReturn("Ice Cream").thenReturn("Burger");
        when(mockResultSet.getString("description")).thenReturn("Delicious vanilla").thenReturn("Juicy beef burger");
        when(mockResultSet.getInt("preparationTime")).thenReturn(10).thenReturn(15);
        when(mockResultSet.getDouble("price")).thenReturn(3.99).thenReturn(7.99);
        when(mockResultSet.getString("ingredients")).thenReturn("Milk, Sugar, Vanilla").thenReturn("Beef, Lettuce, Tomato");
        when(mockResultSet.getInt("categoryId")).thenReturn(1).thenReturn(2);

        // Act
        List<MenuItem> result = menuItemDAO.getAllMenuItems();

        // Assert
        assertNotNull(result, "Menu items list should not be null.");
        assertEquals(2, result.size(), "There should be two menu items.");
        assertEquals("Ice Cream", result.get(0).getName(), "First item should be Ice Cream.");
        assertEquals("Burger", result.get(1).getName(), "Second item should be Burger.");
    }

    @Test
    void updateMenuItem() throws SQLException {
        // Arrange
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);  // Simulate successful update

        // Act
        boolean result = menuItemDAO.updateMenuItem(1, "Ice Cream", "Delicious vanilla", 10, 4.49, "Milk, Sugar, Vanilla", 1);

        // Assert
        assertTrue(result, "Menu item should be updated successfully.");
        verify(mockPreparedStatement).setString(1, "Ice Cream");  // Verify that the correct SQL parameters were set
        verify(mockPreparedStatement).setDouble(4, 4.49);  // Verify that the price was updated correctly
    }
}
