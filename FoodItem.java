class FoodItem {

    String name;
    double price;
    String category;
    boolean available;

    FoodItem(String name, double price, String category) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.available = true;
    }

    public String toString() {
        return "FoodItem{name='" + name + "', price=" + price +
               ", category='" + category + "', available=" + available + "}";
    }
}
