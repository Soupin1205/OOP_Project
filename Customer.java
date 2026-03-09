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
        this.order = new Order(this, cart);
        System.out.println("Order created successfully!");
    }

    // Get the customer's last order
    public Order getOrder() {
        return order;
    }

    // Implementation of abstract method from User
    @Override
    public void showInfo() {
        System.out.println("Customer: " + name);
        System.out.println("Membership: " + (member ? "YES" : "NO"));
    }
}
