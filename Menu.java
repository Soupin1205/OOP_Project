class Menu {

    FoodItem[] items;
    int itemCount;
    String menuName;
    boolean open;

    Menu(String menuName, int capacity) {
        this.menuName = menuName;
        this.items = new FoodItem[capacity];
        this.itemCount = 0;
        this.open = true;
    }

    void addFood(FoodItem food) {
        items[itemCount] = food;
        itemCount++;
    }

    FoodItem findFoodByName(String name) {
        for (int i = 0; i < itemCount; i++) {
            if (items[i].name.equals(name)) { // equals()
                return items[i];
            }
        }
        return null; // null safety
    }

    void printMenu() {
        for (int i = 0; i < itemCount; i++) {
            System.out.println(items[i]);
        }
    }
}
