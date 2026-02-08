class Cart {

    FoodItem[] foods;
    int[] quantities;
    int count;
    double totalPrice;

    Cart(int capacity) {
        foods = new FoodItem[capacity];
        quantities = new int[capacity];
        count = 0;
        totalPrice = 0;
    }

    void addItem(FoodItem food, int qty) {
        foods[count] = food;          // reference
        quantities[count] = qty;      // primitive
        totalPrice += food.price * qty;
        count++;
    }
}
