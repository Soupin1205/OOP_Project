class Order {

    Customer customer;

    String[] itemNames;          // reference array
    double[] priceSnapshot;      // primitive snapshot
    int[] qtySnapshot;           // primitive
    int itemCount;

    Order(Customer customer, Cart cart) {
        this.customer = customer;

        itemNames = new String[cart.count];
        priceSnapshot = new double[cart.count];
        qtySnapshot = new int[cart.count];
        itemCount = cart.count;

        for (int i = 0; i < cart.count; i++) {
            itemNames[i] = cart.foods[i].name;
            priceSnapshot[i] = cart.foods[i].price; // SNAPSHOT
            qtySnapshot[i] = cart.quantities[i];
        }
    }

    double getTotal() {
        double total = 0;
        for (int i = 0; i < itemCount; i++) {
            total += priceSnapshot[i] * qtySnapshot[i];
        }
        return total;
    }
}
