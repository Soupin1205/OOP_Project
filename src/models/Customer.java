package src.models;
import src.service.Cart;
import src.service.Order;


public class Customer extends User {
    private Cart cart;       // encapsulated cart
    private Order order;     // customer's order
    private int points;      // loyalty points

    // Constructor
    public Customer(String name, boolean member) {
        super(name, member); // call parent constructor
        this.cart = new Cart(10); // initialize cart with capacity 10
        this.points = 0;          // start with 0 points
    }

    // Get the customer's cart
    public Cart getCart() {
        return cart;
    }

    // Place an order from the current cart
    public void placeOrder() {
        if (cart.getCount() == 0) {
            System.out.println("Cart is empty! Cannot place order.");
            return;
        }

        this.order = new Order(this, cart);
        System.out.println("Order created successfully!");

        // Optional: Add loyalty points
        addPoints((int) order.getTotal() / 10); // 1 point per $10 spent

        // Clear the cart after placing the order
        cart.clear();
    }

    // Get the customer's last order
    public Order getOrder() {
        return order;
    }
    // Get loyalty points
    public int getPoints() {
        return points;
    }

    // Add points
    public void addPoints(int pts) {
        this.points += pts;
    }

    // Implementation of abstract method from User
    @Override
    public void showInfo() {
        System.out.println("Customer: " + name);
        System.out.println("Membership: " + (member ? "YES" : "NO"));
    }
}
