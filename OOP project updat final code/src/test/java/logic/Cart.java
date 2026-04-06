package logic;

import java.util.LinkedHashMap;
import java.util.Map;

import model.FoodItem;

public class Cart {
    private Map<FoodItem, Integer> items;

    public Cart() {
        items = new LinkedHashMap<>();
    }

    public Map<FoodItem, Integer> getItems() { return items; }

    public void addItem(FoodItem item, int quantity) {
        items.put(item, items.getOrDefault(item, 0) + quantity);
    }

    public void addItem(FoodItem item) {
        addItem(item, 1);
    }

    public boolean removeItem(String foodName) {
        return items.keySet().removeIf(item -> item.getName().equalsIgnoreCase(foodName));
    }

    public boolean updateQuantity(String foodName, int quantity) {
        for (Map.Entry<FoodItem, Integer> entry : items.entrySet()) {
            if (entry.getKey().getName().equalsIgnoreCase(foodName)) {
                if (quantity <= 0) {
                    items.remove(entry.getKey());
                } else {
                    items.put(entry.getKey(), quantity);
                }
                return true;
            }
        }
        return false;
    }

    public void clear() {
        items.clear();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int getTotalItems() {
        return items.values().stream().mapToInt(Integer::intValue).sum();
    }

    public double calculateSubtotal() {
        return items.entrySet().stream()
            .mapToDouble(entry -> entry.getKey().getPrice() * entry.getValue())
            .sum();
    }

    public void display() {
        if (items.isEmpty()) {
            System.out.println("Your cart is empty!");
            return;
        }

        System.out.println("\n========== YOUR CART ==========");
        System.out.printf("%-25s %-8s %-10s %-10s\n", "Item", "Qty", "Price", "Subtotal");
        System.out.println("--------------------------------------------------------");

        for (Map.Entry<FoodItem, Integer> entry : items.entrySet()) {
            FoodItem item = entry.getKey();
            int qty = entry.getValue();
            double subtotal = item.getPrice() * qty;
            System.out.printf("%-25s %-8d $%-9.2f $%-9.2f\n",
                item.getName(), qty, item.getPrice(), subtotal);
        }

        System.out.println("--------------------------------------------------------");
        System.out.printf("Total items: %d\n", getTotalItems());
        System.out.printf("Subtotal: $%.2f\n", calculateSubtotal());
        System.out.println("================================\n");
    }
}