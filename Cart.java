// Cart class
// Represents a customer's shopping cart
public class Cart {
    private FoodItem[] foods;  // reference array of FoodItems
    private int[] quantities;  // quantities for each item
    private int count;         // number of items in cart
    private double totalPrice; // total cart price

    // Constructor with capacity
    public Cart(int capacity) {
        foods = new FoodItem[capacity];
        quantities = new int[capacity];
        count = 0;
        totalPrice = 0;
    }

    // Add item to cart
    public void addItem(FoodItem food, int qty) {
        foods[count] = food;           // store reference to food
        quantities[count] = qty;       // store quantity
        totalPrice += food.getPrice() * qty; // update total
        count++;
    }

    // Getters for encapsulation
    public FoodItem[] getFoods() { return foods; }
    public int[] getQuantities() { return quantities; }
    public int getCount() { return count; }
    public double getTotalPrice() { return totalPrice; }
}
