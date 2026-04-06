package logic;

import java.util.ArrayList;
import java.util.List;

import model.FoodItem;

public class Menu {
    private List<FoodItem> items;

    public Menu() {
        items = new ArrayList<>();
    }

    public void addFood(FoodItem item) {
        items.add(item);
    }

    public FoodItem search(String name) {
        for (FoodItem item : items) {
            if (item.getName().equalsIgnoreCase(name)) {
                return item;
            }
        }
        return null;
    }

    public boolean removeFood(String name) {
        return items.removeIf(item -> item.getName().equalsIgnoreCase(name));
    }

    public List<FoodItem> getItems() {
        return items;
    }

    // Chea Meiya - Formatted menu display
    public void display() {
        System.out.println("\n========== FOOD MENU ==========");
        System.out.printf("%-20s | %-10s | %-10s | %s\n", "Name", "Category", "Price", "Avail");
        System.out.println("--------------------------------------------------------");
        for (FoodItem item : items) {
            System.out.println(item);
        }
        System.out.println("================================\n");
    }
}