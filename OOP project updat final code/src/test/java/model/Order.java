package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import logic.Cart;

public class Order {
    private String orderId;
    private String customerName;
    private String customerPhone;
    private MembershipType membership;
    private List<String> itemNames;
    private List<Double> priceSnapshot;
    private List<Integer> qtySnapshot;
    private double subtotal;
    private double discount;
    private double finalTotal;
    private String timestamp;
    private String status;

    public Order(Cart cart, MembershipType membership, Customer customer) {
        this.orderId = "ORD-" + System.currentTimeMillis();
        this.customerName = customer.getName();
        this.customerPhone = customer.getPhone();
        this.membership = membership;
        this.itemNames = new ArrayList<>();
        this.priceSnapshot = new ArrayList<>();
        this.qtySnapshot = new ArrayList<>();
        this.status = "COMPLETED";
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm:ss"));

        for (Map.Entry<FoodItem, Integer> entry : cart.getItems().entrySet()) {
            itemNames.add(entry.getKey().getName());
            priceSnapshot.add(entry.getKey().getPrice());
            qtySnapshot.add(entry.getValue());
        }
        calculateTotals();
    }

    private void calculateTotals() {
        subtotal = 0;
        for (int i = 0; i < itemNames.size(); i++) {
            subtotal += priceSnapshot.get(i) * qtySnapshot.get(i);
        }
        discount = subtotal * membership.getRate();
        finalTotal = subtotal - discount;
    }

    public void addItem(FoodItem item, int quantity) {
        itemNames.add(item.getName());
        priceSnapshot.add(item.getPrice());
        qtySnapshot.add(quantity);
        calculateTotals();
    }

    public String getOrderId() { return orderId; }
    public double getSubtotal() { return subtotal; }
    public double getDiscount() { return discount; }
    public double getFinalTotal() { return finalTotal; }
    public MembershipType getMembership() { return membership; }
    public List<String> getItemNames() { return itemNames; }

    // Chea Meiya - Formatted receipt
    public void printReceipt() {
        System.out.println("\n****************************************");
        System.out.println("*        CADT DIGITAL CANTEN         *");
        System.out.println("****************************************");
        System.out.println(" Order ID   : " + orderId);
        System.out.println(" Date/Time  : " + timestamp);
        System.out.println(" Customer   : " + customerName + " (" + customerPhone + ")");
        System.out.println(" Membership : " + membership);
        System.out.println("----------------------------------------");
        System.out.printf("%-25s %-4s %-10s\n", "ITEM", "QTY", "SUBTOTAL");
        System.out.println("----------------------------------------");

        for (int i = 0; i < itemNames.size(); i++) {
            double lineTotal = priceSnapshot.get(i) * qtySnapshot.get(i);
            System.out.printf("%-25s x%-3d $%-9.2f\n", itemNames.get(i), qtySnapshot.get(i), lineTotal);
        }

        System.out.println("----------------------------------------");
        System.out.printf(" Subtotal:               $%-10.2f\n", subtotal);
        System.out.printf(" Discount (%.0f%%):      -$%-10.2f\n", membership.getRate() * 100, discount);
        System.out.println("----------------------------------------");
        System.out.printf(" TOTAL TO PAY:           $%-10.2f\n", finalTotal);
        System.out.println("****************************************");
        System.out.println("*     THANK YOU - COME AGAIN!         *");
        System.out.println("****************************************\n");
    }

    public void printSummary() {
        System.out.printf("%-15s | %-15s | %-12s | $%-8.2f | %-10s\n",
            orderId, truncate(customerName, 15), membership, finalTotal, status);
    }

    private String truncate(String str, int length) {
        if (str.length() <= length) return str;
        return str.substring(0, length - 3) + "...";
    }
}