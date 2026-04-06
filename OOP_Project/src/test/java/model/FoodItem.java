package model;

public class FoodItem {
    private final String itemId;
    private final String name;
    private double price;   
    private String category;
    private boolean available;

    public FoodItem(String itemId, String name, double price, String category, boolean available) {
        this.itemId = itemId;
        this.name = name;
        this.price = price;
        this.category = category;
        this.available = available;
    }

    public FoodItem(String itemId, String name) {
        this.itemId = itemId;
        this.name = name;
    }

    public String getItemId() { return itemId; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
    public boolean isAvailable() { return available; }

    public void setPrice(double price) { 
        this.price = price; 
    }
    
    public void setPrice(double price, double discountPercent) {
        this.price = price * (1 - discountPercent / 100);
    }

    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof FoodItem fi) {
            return this.name.equalsIgnoreCase(fi.name);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return name.toLowerCase().hashCode();
    }

    @Override
    public String toString() {
        String status = available ? "Available" : "Unavailable";
        return String.format("%-20s | %-10s | $%-8.2f | %s", name, category, price, status);
    }
}