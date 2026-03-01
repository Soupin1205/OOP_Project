// Order class implements Payable
// Represents a customer's order (snapshot)
public class Order implements Payable {
    private Customer customer;      // customer reference
    private String[] itemNames;     // snapshot of item names
    private double[] priceSnapshot; // snapshot of prices
    private int[] qtySnapshot;      // snapshot of quantities
    private int itemCount;          // number of items

    // Constructor creates snapshot from cart
    public Order(Customer customer, Cart cart) {
        this.customer = customer;
        itemCount = cart.getCount();

        itemNames = new String[itemCount];
        priceSnapshot = new double[itemCount];
        qtySnapshot = new int[itemCount];

        // Copy data from cart (snapshot)
        for (int i = 0; i < itemCount; i++) {
            itemNames[i] = cart.getFoods()[i].getName();
            priceSnapshot[i] = cart.getFoods()[i].getPrice();
            qtySnapshot[i] = cart.getQuantities()[i];
        }
    }

    // Calculate total amount
    @Override
    public double getTotal() {
        double total = 0;
        for (int i = 0; i < itemCount; i++) {
            total += priceSnapshot[i] * qtySnapshot[i];
        }
        return total;
    }
}
