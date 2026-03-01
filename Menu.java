// Menu class
// Represents the restaurant's menu
public class Menu {
    private FoodItem[] items;  // list of items
    private int itemCount;     // number of items
    private String menuName;   // menu title
    private boolean open;      // menu open status

    // Constructor
    public Menu(String menuName, int capacity) {
        this.menuName = menuName;
        this.items = new FoodItem[capacity];
        this.itemCount = 0;
        this.open = true;
    }

    // Add food to menu
    public void addFood(FoodItem food) {
        items[itemCount] = food;
        itemCount++;
    }

    // Find a food by name (returns null if not found)
    public FoodItem findFoodByName(String name) {
        for (int i = 0; i < itemCount; i++) {
            if (items[i].getName().equals(name)) return items[i];
        }
        return null;
    }

    // Print menu items
    public void printMenu() {
        for (int i = 0; i < itemCount; i++) {
            System.out.println(items[i]);
        }
    }
}