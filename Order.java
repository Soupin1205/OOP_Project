public class Order implements Payable {
    private Customer customer;      
    private String[] itemNames;     
    private double[] priceSnapshot; 
    private int[] qtySnapshot;      
    private int itemCount;          

    public Order(Customer customer, Cart cart) {
        this.customer = customer;
        itemCount = cart.getCount();

        itemNames = new String[itemCount];
        priceSnapshot = new double[itemCount];
        qtySnapshot = new int[itemCount];

        for (int i = 0; i < itemCount; i++) {
            itemNames[i] = cart.getFoods()[i].getName();
            priceSnapshot[i] = cart.getFoods()[i].getPrice();
            qtySnapshot[i] = cart.getQuantities()[i];
        }
    }

    @Override
    public double getTotal() {
        double total = 0;
        for (int i = 0; i < itemCount; i++) {
            total += priceSnapshot[i] * qtySnapshot[i];
        }
        return total;
    }

    //  Add these getters
    public int getItemCount() {
        return itemCount;
    }

    public String[] getItemNames() {
        return itemNames;
    }

    public double[] getPriceSnapshot() {
        return priceSnapshot;
    }
}