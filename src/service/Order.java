package src.service;
import java.time.LocalDateTime;

import src.models.Customer;

public class Order implements Payable {
    private Customer customer;      
    private String[] itemNames;     
    private double[] priceSnapshot; 
    private int[] qtySnapshot;      
    private int itemCount;   
    private LocalDateTime orderTime; // timestamp when order was placed       

    public Order(Customer customer, Cart cart) {
        this.customer = customer;
        itemCount = cart.getCount();
        this.orderTime = LocalDateTime.now();

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
     // Print order details
    public void printOrderInfo() {
        System.out.println("Order for customer: " + customer.getName());
        System.out.println("Order Time: " + orderTime);
        System.out.println("Items:");
        for (int i = 0; i < itemCount; i++) {
            System.out.println("- " + itemNames[i] + " x" + qtySnapshot[i] 
                               + " = $" + (priceSnapshot[i] * qtySnapshot[i]));
        }
        System.out.println("Total: $" + getTotal());
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
    public LocalDateTime getOrderTime() {
        return orderTime;
    }
     public int[] getQtySnapshot() {
        return qtySnapshot;
    }
}