// FoodItem class
// Represents a single food item in the menu
public class FoodItem {
    private String name;      // food name
    private double price;     // food price
    private String category;  // category (Fast Food, Dessert, etc.)
    private boolean available; // availability

    // Constructor
    public FoodItem(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.available = true; // default is available
    }

    // Getters & Setters for encapsulation
    public String getName() { return name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getCategory() { return category; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    // Print object info nicely
    @Override
    public String toString() {
        return "FoodItem{name='" + name + "', price=" + price +
               ", category='" + category + "', available=" + available + "}";
    }
}
