package src.service;
import src.models.FoodItem;

public class Menu {
    private FoodItem[] items;
    private int itemCount;
    private String menuName;
    private boolean open;

    public Menu(String menuName, int capacity) {
        this.menuName = menuName;
        items = new FoodItem[capacity];
        itemCount = 0;
        open = true;
    }

    public void addFood(FoodItem food) {
        items[itemCount] = food;
        itemCount++;
    }

    public FoodItem findFoodByName(String name) {
        for (int i = 0; i < itemCount; i++) {
            if (items[i].getName().equals(name)) return items[i];
        }
        return null;
    }

    public void printMenu() {
        if (!open) {
            System.out.println("Menu " + menuName + " is closed.");
            return;
        }
        System.out.println("Menu: " + menuName);
        for (int i = 0; i < itemCount; i++) {
            System.out.println(items[i]);
        }
    }
    public void showMenuInfo() {
    System.out.println("Menu Name: " + menuName);
    System.out.println("Status: " + (open ? "Open" : "Closed"));
    }
    public void openMenu() {
        open = true;
    }

    public void closeMenu() {
        open = false;
    }

    public boolean isOpen() {
        return open;
    }
}