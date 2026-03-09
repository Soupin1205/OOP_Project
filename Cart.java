public class Cart {

    private FoodItem[] foods;
    private int[] quantities;
    private int count;
    private double totalPrice;

    public Cart(int capacity) {
        foods = new FoodItem[capacity];
        quantities = new int[capacity];
        count = 0;
        totalPrice = 0;
    }

    // Add item with quantity
    public void addItem(FoodItem food, int qty) {

        if (count >= foods.length) {
            System.out.println("Cart is full!");
            return;
        }

        foods[count] = food;
        quantities[count] = qty;
        totalPrice += food.getPrice() * qty;
        count++;
    }

    // Method Overloading
    public void addItem(FoodItem food) {
        addItem(food, 1);
    }

    public FoodItem[] getFoods() {
        return foods;
    }

    public int[] getQuantities() {
        return quantities;
    }

    public int getCount() {
        return count;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

}