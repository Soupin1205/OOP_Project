package controller;

import java.util.ArrayList;
import java.util.List;

import logic.Menu;
import model.Admin;
import model.Customer;
import model.FoodItem;
import model.MembershipType;
import model.Order;
import model.User;

public class FoodShopController {
    private final String shopName;
    private final String address;
    private final Menu menu;
    private final List<Customer> customers;
    private Admin admin;
    private final List<Order> allOrders;
    private User currentUser;
    private String lastMessage;

    public FoodShopController(String shopName, String address) {
        this.shopName = shopName;
        this.address = address;
        this.menu = new Menu();
        this.customers = new ArrayList<>();
        this.allOrders = new ArrayList<>();
        this.currentUser = null;
        this.lastMessage = "";
        initializeDefaultData();
    }

    private void initializeDefaultData() {
        // Add default food items
        menu.addFood(new FoodItem("F001", "Pepperoni Pizza", 12.0, "Main", true));
        menu.addFood(new FoodItem("F002", "Margherita Pizza", 10.0, "Main", true));
        menu.addFood(new FoodItem("F003", "Coca Cola", 1.5, "Drink", true));
        menu.addFood(new FoodItem("F004", "Sprite", 1.5, "Drink", true));
        menu.addFood(new FoodItem("F005", "Chocolate Cake", 4.0, "Dessert", true));
        menu.addFood(new FoodItem("F006", "French Fries", 3.0, "Snack", true));
        menu.addFood(new FoodItem("F007", "Ice Americano", 2.5, "Drink", true));
        menu.addFood(new FoodItem("F008", "Chicken Burger", 5.0, "Main", true));

        // Create admin
        admin = new Admin("A001", "admin", "admin123", "SUPER");

        // Add default customers
        customers.add(new Customer("C0001", "Dane Smith", "011111111", "pass123", 100.0, MembershipType.GOLD));
        customers.add(new Customer("C0002", "Somen Doe", "022222222", "pass123", 50.0, MembershipType.SILVER));
        customers.add(new Customer("C0003", "Regular User", "033333333", "pass123", 30.0, MembershipType.NONE));
    }

    public String getShopName() { return shopName; }
    public String getAddress() { return address; }
    public String getLastMessage() { return lastMessage; }
    public boolean isLoggedIn() { return currentUser != null; }
    public String getCurrentUserName() { return currentUser != null ? currentUser.getName() : "None"; }
    
    public String getUserType() {
        if (currentUser instanceof Customer) return "Customer";
        if (currentUser instanceof Admin) return "Admin";
        return "Unknown";
    }
    
    public String getMembershipInfo() {
        if (currentUser instanceof Customer customer) {
            return customer.getMembership().toString();
        }
        if (currentUser instanceof Admin adminUser) {
            return adminUser.getAdminLevel();
        }
        return "None";
    }
    
    public boolean isAdmin() {
        return currentUser instanceof Admin;
    }

    private void setLastMessage(String msg) { this.lastMessage = msg; }

    private boolean requireLogin() {
        if (currentUser == null) {
            setLastMessage("Please login first!");
            return false;
        }
        return true;
    }

    private boolean requireAdmin() {
        if (!(currentUser instanceof Admin)) {
            setLastMessage("Admin access required!");
            return false;
        }
        return true;
    }

    public void login(String username, String password) {
        // Check admin first
        if (admin.getName().equals(username) && admin.checkPassword(password)) {
            if (admin.isActive()) {
                currentUser = admin;
                setLastMessage("Welcome " + admin.getName() + "!");
                return;
            }
        }
        
        // Check customers
        for (Customer c : customers) {
            if (c.getPhone().equals(username) && c.checkPassword(password)) {
                if (c.isActive()) {
                    currentUser = c;
                    setLastMessage("Welcome " + c.getName() + "! Membership: " + c.getMembership());
                    return;
                }
            }
        }
        setLastMessage("Login failed! Use phone number for customer or 'admin' for admin");
    }

    public void logout() {
        currentUser = null;
        setLastMessage("Logged out successfully!");
    }

    public void registerAsGuest(String phone, String name) {
        for (Customer c : customers) {
            if (c.getPhone().equals(phone)) {
                setLastMessage("Phone number already registered! Please login.");
                return;
            }
        }
        String customerId = "C" + String.format("%04d", customers.size() + 1);
        Customer newCustomer = new Customer(customerId, name, phone, "1234", 0.0, MembershipType.NONE);
        customers.add(newCustomer);
        currentUser = newCustomer;
        setLastMessage("Account created successfully!");
    }

    public void viewMenu() {
        menu.display();
    }

    public void addToCart(String foodName, int quantity) {
        if (!requireLogin()) return;
        if (!(currentUser instanceof Customer customer)) {
            setLastMessage("Only customers can add items to cart!");
            return;
        }

        FoodItem item = menu.search(foodName);
        if (item == null) {
            setLastMessage("Food item not found!");
            return;
        }

        if (!item.isAvailable()) {
            setLastMessage("Sorry, " + foodName + " is currently unavailable!");
            return;
        }

        if (quantity <= 0 || quantity > 99) {
            setLastMessage("Invalid quantity! Please enter between 1-99.");
            return;
        }

        customer.getCart().addItem(item, quantity);
        setLastMessage(quantity + " x " + foodName + " added to cart!");
    }

    public void viewCart() {
        if (!requireLogin()) return;
        if (!(currentUser instanceof Customer customer)) {
            setLastMessage("Only customers have carts!");
            return;
        }
        customer.getCart().display();
    }

    public void removeFromCart(String foodName) {
        if (!requireLogin()) return;
        if (!(currentUser instanceof Customer customer)) {
            setLastMessage("Only customers can modify cart!");
            return;
        }
        if (customer.getCart().removeItem(foodName)) {
            setLastMessage(foodName + " removed from cart!");
        } else {
            setLastMessage(foodName + " not found in cart!");
        }
    }

    public void updateCartQuantity(String foodName, int quantity) {
        if (!requireLogin()) return;
        if (!(currentUser instanceof Customer customer)) {
            setLastMessage("Only customers can modify cart!");
            return;
        }
        if (quantity <= 0) {
            removeFromCart(foodName);
            return;
        }
        if (customer.getCart().updateQuantity(foodName, quantity)) {
            setLastMessage(foodName + " quantity updated to " + quantity);
        } else {
            setLastMessage("Item not found in cart!");
        }
    }

    public void placeOrder() {
        if (!requireLogin()) return;
        if (!(currentUser instanceof Customer customer)) {
            setLastMessage("Only customers can place orders!");
            return;
        }

        if (customer.getCart().isEmpty()) {
            setLastMessage("Cart is empty! Add items first.");
            return;
        }

        double subtotal = customer.getCart().calculateSubtotal();
        double discount = subtotal * customer.getMembership().getRate();
        double finalTotal = subtotal - discount;

        if (customer.getBalance() < finalTotal) {
            setLastMessage(String.format("Insufficient balance! Need: $%.2f, Available: $%.2f", finalTotal, customer.getBalance()));
            return;
        }

        Order order = new Order(customer.getCart(), customer.getMembership(), customer);
        customer.deductBalance(finalTotal);
        customer.addOrder(order);
        allOrders.add(order);
        customer.getCart().clear();
        setLastMessage(String.format("Order placed successfully! Total: $%.2f (Saved: $%.2f)", finalTotal, discount));
        order.printReceipt();
    }

    public void viewOrderHistory() {
        if (!requireLogin()) return;
        if (!(currentUser instanceof Customer customer)) {
            setLastMessage("Only customers have order history!");
            return;
        }
        customer.viewOrderHistory();
    }

    public void viewLastReceipt() {
        if (!requireLogin()) return;
        if (!(currentUser instanceof Customer customer)) {
            setLastMessage("Only customers have receipts!");
            return;
        }
        customer.viewLastReceipt();
    }

    public void addBalance(double amount) {
        if (!requireLogin()) return;
        if (!(currentUser instanceof Customer customer)) {
            setLastMessage("Only customers can add balance!");
            return;
        }
        if (amount <= 0) {
            setLastMessage("Invalid amount! Must be greater than 0.");
            return;
        }
        customer.addBalance(amount);
        setLastMessage(String.format("Added $%.2f. New balance: $%.2f", amount, customer.getBalance()));
    }

    public void checkBalance() {
        if (!requireLogin()) return;
        if (!(currentUser instanceof Customer customer)) {
            setLastMessage("Only customers have balances!");
            return;
        }
        System.out.printf("\nCurrent balance: $%.2f\n", customer.getBalance());
        System.out.printf("Membership discount: %.0f%%\n", customer.getMembership().getRate() * 100);
    }

    // Admin methods
    public void addFoodItem(String name, double price, String category) {
        if (!requireAdmin()) return;
        if (menu.search(name) != null) {
            setLastMessage("Food item already exists!");
            return;
        }
        if (price <= 0) {
            setLastMessage("Price must be positive!");
            return;
        }
        String itemId = "F" + String.format("%03d", menu.getItems().size() + 1);
        admin.addFoodItem(menu, itemId, name, price, category);
        setLastMessage("Added " + name + " to menu!");
    }

    public void updateFoodPrice(String name, double newPrice) {
        if (!requireAdmin()) return;
        if (newPrice <= 0) {
            setLastMessage("Price must be positive!");
            return;
        }
        admin.updateFoodPrice(menu, name, newPrice);
        setLastMessage("Price updated for " + name);
    }

    public void removeFoodItem(String name) {
        if (!requireAdmin()) return;
        admin.removeFoodItem(menu, name);
        setLastMessage("Removed " + name + " from menu!");
    }

    public void setFoodAvailability(String name, boolean available) {
        if (!requireAdmin()) return;
        admin.setFoodAvailability(menu, name, available);
        setLastMessage(name + " is now " + (available ? "available" : "unavailable"));
    }

    public void viewAllCustomers() {
        if (!requireAdmin()) return;
        admin.viewAllCustomers(customers);
    }

    public void viewAllOrders() {
        if (!requireAdmin()) return;
        admin.viewAllOrders(allOrders);
    }

    public void viewSystemStats() {
        if (!requireAdmin()) return;
        admin.viewSystemStats(customers, allOrders, menu);
    }
}