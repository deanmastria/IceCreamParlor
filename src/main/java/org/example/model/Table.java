package org.example.model;

public class Table {
    private int id;
    private int size;
    private String status;

    // Constructor
    public Table(int id, int size, String status) {
        this.id = id;
        this.size = size;
        this.status = status;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Table{" +
                "id=" + id +
                ", size=" + size +
                ", status='" + status + '\'' +
                '}';
    }
}
