class Customer {

    String name;
    Cart cart;
    Order order;
    boolean member;
    int points;

    Customer(String name, boolean member) {
        this.name = name;
        this.member = member;
        this.points = 0;
        this.cart = new Cart(10);
    }

    void placeOrder() {
        order = new Order(this, cart);
    }
}
