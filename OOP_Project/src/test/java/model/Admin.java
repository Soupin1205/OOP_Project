package model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import logic.Menu;

public class Admin extends User {
    private final String adminLevel;

    public Admin(String userId, String name, String password, String adminLevel) {
        super(userId, name, password);
        this.adminLevel = adminLevel;
    }

    public String getAdminLevel() { return adminLevel; }

    public void addFoodItem(Menu menu, String itemId, String name, double price, String category) {
        FoodItem newItem = new FoodItem(itemId, name, price, category, true);
        menu.addFood(newItem);
        System.out.println("[ADMIN] Added " + name + " to menu!");
    }

    public void updateFoodPrice(Menu menu, String foodName, double newPrice) {
        FoodItem item = menu.search(foodName);
        if (item != null) {
            double oldPrice = item.getPrice();
            item.setPrice(newPrice);
            System.out.printf("[ADMIN] Price updated: %s $%.2f → $%.2f\n", foodName, oldPrice, newPrice);
        } else {
            System.out.println("[ADMIN] Food item not found!");
        }
    }

    public void removeFoodItem(Menu menu, String foodName) {
        if (menu.removeFood(foodName)) {
            System.out.println("[ADMIN] Removed " + foodName + " from menu!");
        } else {
            System.out.println("[ADMIN] Food item not found!");
        }
    }

    public void setFoodAvailability(Menu menu, String foodName, boolean available) {
        FoodItem item = menu.search(foodName);
        if (item != null) {
            item.setAvailable(available);
            System.out.println("[ADMIN] " + foodName + " is now " + (available ? "available" : "unavailable"));
        } else {
            System.out.println("[ADMIN] Food item not found!");
        }
    }

    public void viewAllCustomers(List<Customer> customers) {
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