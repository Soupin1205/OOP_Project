package model;

import logic.Menu;
import model.Order;
import java.util.*;

public class Admin extends User {
    private String adminLevel;
    private List<String> actionLog;

    public Admin(String userId, String name, String password, String adminLevel) {
        super(userId, name, password);
        this.adminLevel = adminLevel;
        this.actionLog = new ArrayList<>();
    }

    public String getAdminLevel() { return adminLevel; }
    public List<String> getActionLog() { return actionLog; }

    public void addFoodItem(Menu menu, String itemId, String name, double price, String category) {
        if (!canManageMenu()) {
            logAction("FAILED: Add food item - Permission denied");
            return;
        }
        FoodItem newItem = new FoodItem(itemId, name, price, category, true);
        menu.addFood(newItem);
        logAction("Added food item: " + name + " ($" + price + ")");
        System.out.println("[ADMIN] Added " + name + " to menu!");
    }

    public void updateFoodPrice(Menu menu, String foodName, double newPrice) {
        if (!canManageMenu()) {
            logAction("FAILED: Update price - Permission denied");
            return;
        }
        FoodItem item = menu.search(foodName);
        if (item != null) {
            double oldPrice = item.getPrice();
            item.setPrice(newPrice);
            logAction("Updated price: " + foodName + " from $" + oldPrice + " to $" + newPrice);
            System.out.printf("[ADMIN] Price updated: %s $%.2f → $%.2f\n", foodName, oldPrice, newPrice);
        } else {
            System.out.println("[ADMIN] Food item not found!");
        }
    }

    public void removeFoodItem(Menu menu, String foodName) {
        if (!canManageMenu()) {
            logAction("FAILED: Remove food item - Permission denied");
            return;
        }
        if (menu.removeFood(foodName)) {
            logAction("Removed food item: " + foodName);
            System.out.println("[ADMIN] Removed " + foodName + " from menu!");
        } else {
            System.out.println("[ADMIN] Food item not found!");
        }
    }

    public void setFoodAvailability(Menu menu, String foodName, boolean available) {
        if (!canManageMenu()) {
            logAction("FAILED: Set availability - Permission denied");
            return;
        }
        FoodItem item = menu.search(foodName);
        if (item != null) {
            item.setAvailable(available);
            logAction("Set " + foodName + " availability to " + (available ? "AVAILABLE" : "UNAVAILABLE"));
            System.out.println("[ADMIN] " + foodName + " is now " + (available ? "available" : "unavailable"));
        } else {
            System.out.println("[ADMIN] Food item not found!");
        }
    }

    public void applyDiscountToItem(Menu menu, String foodName, double discountPercent) {
        if (!canManageMenu()) {
            logAction("FAILED: Apply discount - Permission denied");
            return;
        }
        FoodItem item = menu.search(foodName);
        if (item != null) {
            double oldPrice = item.getPrice();
            item.setPrice(oldPrice, discountPercent);
            logAction("Applied " + discountPercent + "% discount to " + foodName);
            System.out.printf("[ADMIN] Applied %.0f%% discount to %s: $%.2f → $%.2f\n",
                discountPercent, foodName, oldPrice, item.getPrice());
        } else {
            System.out.println("[ADMIN] Food item not found!");
        }
    }

    public void viewAllCustomers(List<Customer> customers) {
        if (!canViewAll()) {
            logAction("FAILED: View customers - Permission denied");
            return;
        }
        System.out.println("\n========== ALL CUSTOMERS ==========");
        System.out.printf("%-10s %-20s %-15s %-12s %-10s %-8s\n", "ID", "Name", "Phone", "Membership", "Balance", "Active");
        System.out.println("--------------------------------------------------------------------------------");
        for (Customer c : customers) {
            System.out.printf("%-10s %-20s %-15s %-12s $%-9.2f %-8s\n",
                c.getCustomerId(), truncate(c.getName(), 20), c.getPhone(),
                c.getMembership(), c.getBalance(), c.isActive() ? "Yes" : "No");
        }
    }

    public void viewAllOrders(List<Order> orders) {
        if (!canViewAll()) {
            logAction("FAILED: View orders - Permission denied");
            return;
        }
        System.out.println("\n========== ALL ORDERS ==========");
        if (orders.isEmpty()) {
            System.out.println("No orders placed yet.");
            return;
        }
        double totalRevenue = 0;
        for (Order o : orders) {
            o.printSummary();
            totalRevenue += o.getFinalTotal();
        }
        System.out.printf("\nTotal Revenue: $%.2f\n", totalRevenue);
    }

    public void viewSystemStats(List<Customer> customers, List<Order> orders, Menu menu) {
        if (!canViewAll()) {
            logAction("FAILED: View stats - Permission denied");
            return;
        }
        double totalRevenue = 0;
        int totalOrders = orders.size();
        Map<String, Integer> popularItems = new HashMap<>();
        Map<MembershipType, Integer> memberOrders = new HashMap<>();

        for (Order o : orders) {
            totalRevenue += o.getFinalTotal();
            memberOrders.put(o.getMembership(), memberOrders.getOrDefault(o.getMembership(), 0) + 1);
            for (String item : o.getItemNames()) {
                popularItems.put(item, popularItems.getOrDefault(item, 0) + 1);
            }
        }

        System.out.println("\n========== SYSTEM STATISTICS ==========");
        System.out.println("Total Customers: " + customers.size());
        System.out.println("Total Menu Items: " + menu.getItems().size());
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

    public void upgradeCustomerMembership(Customer customer) {
        if (!canManageUsers()) {
            logAction("FAILED: Upgrade membership - Permission denied");
            return;
        }
        MembershipType current = customer.getMembership();
        MembershipType newMembership = MembershipType.getByBalance(customer.getBalance());
        if (current != newMembership) {
            System.out.printf("[ADMIN] Upgraded %s from %s to %s\n",
                customer.getName(), current, newMembership);
            logAction("Upgraded " + customer.getName() + " membership to " + newMembership);
        }
    }

    public void viewActionLog() {
        System.out.println("\n========== ADMIN ACTION LOG ==========");
        if (actionLog.isEmpty()) {
            System.out.println("No actions logged yet.");
            return;
        }
        for (String action : actionLog) {
            System.out.println("  " + action);
        }
    }

    private boolean canManageMenu() {
        return adminLevel.equals("SUPER") || adminLevel.equals("MANAGER");
    }

    private boolean canManageUsers() {
        return adminLevel.equals("SUPER");
    }

    private boolean canViewAll() {
        return adminLevel.equals("SUPER") || adminLevel.equals("MANAGER") || adminLevel.equals("STAFF");
    }

    private void logAction(String action) {
        String timestamp = new Date().toString();
        actionLog.add("[" + timestamp + "] " + action);
    }

    private String truncate(String str, int length) {
        if (str.length() <= length) return str;
        return str.substring(0, length - 3) + "...";
    }

    @Override
    public void showInfo() {
        System.out.printf("[ADMIN] %s | ID: %s | Level: %s | Active: %s\n",
                         name, userId, adminLevel, active);
    }
}