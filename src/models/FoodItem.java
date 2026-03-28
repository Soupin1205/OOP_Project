package src.models;
public class FoodItem {
    private String name;      
    private double price;     
    private String category;  
    private boolean available; 

    // Constructor
    public FoodItem(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.available = true;
    }

    // Getters & Setters
    public String getName() { return name; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    // Overloaded method for discount
    public void setPrice(double price, double discount) {
        this.price = price - discount;
        System.out.println(name + " discount applied: $" + discount + ", new price = $" + this.price);
    }

    public String getCategory() { return category; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
    return name + " - $" + String.format("%.2f", price) 
           + " (" + category + ") " 
           + (available ? "[Available]" : "[Not Available]");
    }
}