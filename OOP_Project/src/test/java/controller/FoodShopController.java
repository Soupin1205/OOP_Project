package controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import logic.Menu;
import model.Customer;
import model.FoodItem;
import model.MembershipType;
import model.Order;
import model.Staff;
import model.User;

public class FoodShopController {
    private String shopName;
    private String address;
    private Menu menu;
    private List<Customer> customers;
    private List<Staff> staffs;
    private List<Order> allOrders;
    private User currentUser;
    private String lastMessage;

    public static final String ADD_FOOD = "ADD_FOOD";
    public static final String UPDATE_PRICE = "UPDATE_PRICE";
    public static final String REMOVE_FOOD = "REMOVE_FOOD";
    public static final String VIEW_ALL_CUSTOMERS = "VIEW_ALL_CUSTOMERS";
    public static final String VIEW_ALL_ORDERS = "VIEW_ALL_ORDERS";
    public static final String CREATE_STAFF = "CREATE_STAFF";
    public static final String UPDATE_SALARY = "UPDATE_SALARY";

    public FoodShopController(String shopName, String address) {
        this.shopName = shopName;
        this.address = address;
        this.menu = new Menu();
        this.customers = new ArrayList<>();
        this.staffs = new ArrayList<>();
        this.allOrders = new ArrayList<>();
        this.currentUser = null;
        this.lastMessage = "";
        initializeDefaultData();
    }

    private void initializeDefaultData() {
        menu.addFood(new FoodItem("F001", "Pepperoni Pizza", 12.0, "Main", true));
        menu.addFood(new FoodItem("F002", "Margherita Pizza", 10.0, "Main", true));
        menu.addFood(new FoodItem("F003", "Coca Cola", 1.5, "Drink", true));
        menu.addFood(new FoodItem("F004", "Sprite", 1.5, "Drink", true));
        menu.addFood(new FoodItem("F005", "Chocolate Cake", 4.0, "Dessert", true));
        menu.addFood(new FoodItem("F006", "French Fries", 3.0, "Snack", true));
        menu.addFood(new FoodItem("F007", "Ice Americano", 2.5, "Drink", true));
        menu.addFood(new FoodItem("F008", "Chicken Burger", 5.0, "Main", true));

        staffs.add(new Staff("S001", "System Admin", "admin", "admin123", "Admin", 5000));
        staffs.add(new Staff("S002", "John Manager", "manager", "manager123", "Manager", 3000));
        staffs.add(new Staff("S003", "Sarah Cashier", "cashier", "cashier123", "Staff", 1500));

        customers.add(new Customer("C001", "Dane Smith", "011111111", "pass123", 100.0, MembershipType.GOLD));
        customers.add(new Customer("C002", "Somen Doe", "022222222", "pass123", 50.0, MembershipType.SILVER));
        customers.add(new Customer("C003", "Regular User", "033333333", "pass123", 30.0, MembershipType.NONE));
    }

    public String getShopName() { return shopName; }
    public String getAddress() { return address; }
    public String getLastMessage() { return lastMessage; }
    public boolean isLoggedIn() { return currentUser != null; }
    public String getCurrentUserName() { return currentUser != null ? currentUser.getName() : "None"; }
    
    public String getUserType() {
        if (currentUser instanceof Customer) return "Customer";
        if (currentUser instanceof Staff) return "Staff";
        return "Unknown";
    }
    
    public String getMembershipInfo() {
        if (currentUser instanceof Customer) {
            return ((Customer) currentUser).getMembership().toString();
        }
        if (currentUser instanceof Staff) {
            return ((Staff) currentUser).getRole();
        }
        return "None";
    }
    
    public boolean isAdmin() {
        return currentUser instanceof Staff && ((Staff) currentUser).getRole().equals("Admin");
    }

    private void setLastMessage(String msg) { this.lastMessage = msg; }

    private boolean requireLogin() {
        if (currentUser == null) {
            setLastMessage("Please login first!");
            return false;
        }
        return true;
    }

    private boolean requirePermission(String action) {
        if (!(currentUser instanceof Staff)) {
            setLastMessage("Permission denied: Staff access required!");
            return false;
        }
        if (!((Staff) currentUser).can(action)) {
            setLastMessage("Permission denied for action: " + action);
            return false;
        }
        return true;
    }

    public void login(String username, String password) {
        for (Staff s : staffs) {
            if (s.getUsername().equals(username) && s.checkPassword(password)) {
                if (s.isActive()) {
                    currentUser = s;
                    setLastMessage("Welcome " + s.getName() + "! Role: " + s.getRole());
                    return;
                }
            }
        }
        for (Customer c : customers) {
            if (c.getPhone().equals(username) && c.checkPassword(password)) {
                if (c.isActive()) {
                    currentUser = c;
                    setLastMessage("Welcome " + c.getName() + "! Membership: " + c.getMembership());
                    return;
                }
            }
        }
        setLastMessage("Login failed: Invalid username/phone or password!");
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
        setLastMessage("Account created successfully! Please add balance to order. Default password: 1234");
    }

    public void viewMenu() {
        menu.display();
    }

    public void addToCart(String foodName, int quantity) {
        if (!requireLogin()) return;
        if (!(currentUser instanceof Customer)) {
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

        Customer customer = (Customer) currentUser;
        customer.getCart().addItem(item, quantity);
        setLastMessage(quantity + " x " + foodName + " added to cart! Total items: " + customer.getCart().getTotalItems());
    }

    public void viewCart() {
        if (!requireLogin()) return;
        if (!(currentUser instanceof Customer)) {
            setLastMessage("Only customers have carts!");
            return;
        }
        Customer customer = (Customer) currentUser;
        customer.getCart().display();
    }

    public void removeFromCart(String foodName) {
        if (!requireLogin()) return;
        if (!(currentUser instanceof Customer)) {
            setLastMessage("Only customers can modify cart!");
            return;
        }
        Customer customer = (Customer) currentUser;
        if (customer.getCart().removeItem(foodName)) {
            setLastMessage(foodName + " removed from cart!");
        } else {
            setLastMessage(foodName + " not found in cart!");
        }
    }

    public void updateCartQuantity(String foodName, int quantity) {
        if (!requireLogin()) return;
        if (!(currentUser instanceof Customer)) {
            setLastMessage("Only customers can modify cart!");
            return;
        }
        if (quantity <= 0) {
            removeFromCart(foodName);
            return;
        }
        Customer customer = (Customer) currentUser;
        if (customer.getCart().updateQuantity(foodName, quantity)) {
            setLastMessage(foodName + " quantity updated to " + quantity);
        } else {
            setLastMessage("Item not found in cart!");
        }
    }

    public void placeOrder() {
        if (!requireLogin()) return;
        if (!(currentUser instanceof Customer)) {
            setLastMessage("Only customers can place orders!");
            return;
        }

        Customer customer = (Customer) currentUser;
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

        for (Map.Entry<FoodItem, Integer> entry : customer.getCart().getItems().entrySet()) {
            order.addItem(entry.getKey(), entry.getValue());
        }

        customer.getCart().clear();
        setLastMessage(String.format("Order placed successfully! Total: $%.2f (Saved: $%.2f)", finalTotal, discount));
        order.printReceipt();
    }

    public void viewOrderHistory() {
        if (!requireLogin()) return;
        if (!(currentUser instanceof Customer)) {
            setLastMessage("Only customers have order history!");
            return;
        }
        Customer customer = (Customer) currentUser;
        customer.viewOrderHistory();
    }

    public void viewLastReceipt() {
        if (!requireLogin()) return;
        if (!(currentUser instanceof Customer)) {
            setLastMessage("Only customers have receipts!");
            return;
        }
        Customer customer = (Customer) currentUser;
        customer.viewLastReceipt();
    }

    public void addBalance(double amount) {
        if (!requireLogin()) return;
        if (!(currentUser instanceof Customer)) {
            setLastMessage("Only customers can add balance!");
            return;
        }
        if (amount <= 0) {
            setLastMessage("Invalid amount! Must be greater than 0.");
            return;
        }
        Customer customer = (Customer) currentUser;
        customer.addBalance(amount);
        setLastMessage(String.format("Added $%.2f. New balance: $%.2f", amount, customer.getBalance()));
    }

    public void checkBalance() {
        if (!requireLogin()) return;
        if (!(currentUser instanceof Customer)) {
            setLastMessage("Only customers have balances!");
            return;
        }
        Customer customer = (Customer) currentUser;
        System.out.printf("\nCurrent balance: $%.2f\n", customer.getBalance());
        System.out.printf("Membership discount: %.0f%%\n", customer.getMembership().getRate() * 100);
    }

    public void addFoodItem(String name, double price, String category) {
        if (!requirePermission(ADD_FOOD)) return;
        if (menu.search(name) != null) {
            setLastMessage("Food item already exists!");
            return;
        }
        if (price <= 0) {
            setLastMessage("Price must be positive!");
            return;
        }
        String itemId = "F" + String.format("%03d", menu.getItems().size() + 1);
        menu.addFood(new FoodItem(itemId, name, price, category, true));
        setLastMessage("Added " + name + " to menu!");
    }

    public void updateFoodPrice(String name, double newPrice) {
        if (!requirePermission(UPDATE_PRICE)) return;
        FoodItem item = menu.search(name);
        if (item == null) {
            setLastMessage("Food item not found!");
            return;
        }
        if (newPrice <= 0) {
            setLastMessage("Price must be positive!");
            return;
        }
        double oldPrice = item.getPrice();
        item.setPrice(newPrice);
        setLastMessage(String.format("Price updated: %s $%.2f → $%.2f", name, oldPrice, newPrice));
    }

    public void removeFoodItem(String name) {
        if (!requirePermission(REMOVE_FOOD)) return;
        if (menu.removeFood(name)) {
            setLastMessage("Removed " + name + " from menu!");
        } else {
            setLastMessage("Food item not found!");
        }
    }

    public void setFoodAvailability(String name, boolean available) {
        if (!requirePermission(UPDATE_PRICE)) return;
        FoodItem item = menu.search(name);
        if (item == null) {
            setLastMessage("Food item not found!");
            return;
        }
        item.setAvailable(available);
        setLastMessage(name + " is now " + (available ? "available" : "unavailable"));
    }

    public void viewAllCustomers() {
        if (!requirePermission(VIEW_ALL_CUSTOMERS)) return;
        System.out.println("\n========== ALL CUSTOMERS ==========");
        System.out.printf("%-10s %-20s %-15s %-12s %-10s %-8s\n", "ID", "Name", "Phone", "Membership", "Balance", "Active");
        System.out.println("--------------------------------------------------------------------------------");
        for (Customer c : customers) {
            System.out.printf("%-10s %-20s %-15s %-12s $%-9.2f %-8s\n",
                c.getCustomerId(), truncate(c.getName(), 20), c.getPhone(),
                c.getMembership(), c.getBalance(), c.isActive() ? "Yes" : "No");
        }
    }

    public void viewAllOrders() {
        if (!requirePermission(VIEW_ALL_ORDERS)) return;
        System.out.println("\n========== ALL ORDERS ==========");
        if (allOrders.isEmpty()) {
            System.out.println("No orders placed yet.");
            return;
        }
        for (Order o : allOrders) {
            o.printSummary();
        }
    }

    public void viewSystemStats() {
        if (!requirePermission(VIEW_ALL_ORDERS)) return;
        double totalRevenue = 0;
        int totalOrders = allOrders.size();
        Map<String, Integer> popularItems = new HashMap<>();
        Map<MembershipType, Integer> memberOrders = new HashMap<>();

        for (Order o : allOrders) {
            totalRevenue += o.getFinalTotal();
            memberOrders.put(o.getMembership(), memberOrders.getOrDefault(o.getMembership(), 0) + 1);
            for (String item : o.getItemNames()) {
                popularItems.put(item, popularItems.getOrDefault(item, 0) + 1);
            }
        }

        System.out.println("\n========== SYSTEM STATISTICS ==========");
        System.out.println("Total Customers: " + customers.size());
        System.out.println("Total Staff: " + staffs.size());
        System.out.println("Total Orders: " + totalOrders);
        System.out.printf("Total Revenue: $%.2f\n", totalRevenue);
        System.out.printf("Average Order Value: $%.2f\n", totalOrders > 0 ? totalRevenue / totalOrders : 0);

        System.out.println("\nOrders by Membership:");
        for (Map.Entry<MembershipType, Integer> entry : memberOrders.entrySet()) {
            System.out.printf("  %s: %d orders\n", entry.getKey(), entry.getValue());
        }

        System.out.println("\nMost Popular Items:");
        popularItems.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
            .limit(5)
            .forEach(entry -> System.out.println("  " + entry.getKey() + ": " + entry.getValue() + " orders"));
    }

    public void createStaff(String name, String username, String password, String role, double salary) {
        if (!requirePermission(CREATE_STAFF)) return;
        for (Staff s : staffs) {
            if (s.getUsername().equals(username)) {
                setLastMessage("Username already exists!");
                return;
            }
        }
        if (salary < 0) {
            setLastMessage("Salary must be positive!");
            return;
        }
        String staffId = "S" + String.format("%03d", staffs.size() + 1);
        Staff newStaff = new Staff(staffId, name, username, password, role, salary);
        staffs.add(newStaff);
        setLastMessage("Staff created: " + name + " (" + role + ") with salary $" + salary);
    }

    public void updateStaffSalary(String username, double newSalary) {
        if (!requirePermission(UPDATE_SALARY)) return;
        for (Staff s : staffs) {
            if (s.getUsername().equals(username)) {
                double oldSalary = s.getSalary();
                s.setSalary(newSalary);
                setLastMessage(String.format("Salary updated for %s: $%.2f → $%.2f", username, oldSalary, newSalary));
                return;
            }
        }
        setLastMessage("Staff not found!");
    }

    public void viewAllStaff() {
        if (!requirePermission(VIEW_ALL_CUSTOMERS)) return;
        System.out.println("\n========== ALL STAFF ==========");
        System.out.printf("%-10s %-20s %-15s %-12s %-10s %-10s\n", "ID", "Name", "Username", "Role", "Salary", "Active");
        System.out.println("--------------------------------------------------------------------------------");
        for (Staff s : staffs) {
            System.out.printf("%-10s %-20s %-15s %-12s $%-9.2f %-10s\n",
                s.getStaffId(), truncate(s.getName(), 20), s.getUsername(),
                s.getRole(), s.getSalary(), s.isActive() ? "Yes" : "No");
        }
    }

    private String truncate(String str, int length) {
        if (str.length() <= length) return str;
        return str.substring(0, length - 3) + "...";
    }
}