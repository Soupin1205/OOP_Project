package model;

import logic.Cart;
import java.util.*;

public class Customer extends User {
    private final String phone;
    private double balance;
    private MembershipType membership;
    private final Cart cart;
    private final List<Order> orderHistory;

    public Customer(String customerId, String name, String phone, String password,
                    double balance, MembershipType membership) {
        super(customerId, name, password);
        this.phone = phone;
        this.balance = balance;
        this.membership = membership;
        this.cart = new Cart();
        this.orderHistory = new ArrayList<>();
    }

    public String getPhone() { return phone; }
    public double getBalance() { return balance; }
    public MembershipType getMembership() { return membership; }
    public Cart getCart() { return cart; }
    public String getCustomerId() { return userId; }

    public void addBalance(double amount) {
        if (amount > 0) balance += amount;
    }

    public boolean deductBalance(double amount) {
        if (amount <= balance && amount > 0) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public void addOrder(Order order) {
        orderHistory.add(order);
    }

    public void viewOrderHistory() {
        if (orderHistory.isEmpty()) {
            System.out.println("No orders yet!");
            return;
        }
        System.out.println("\n========== ORDER HISTORY ==========");
        for (int i = 0; i < orderHistory.size(); i++) {
            System.out.print((i + 1) + ". ");
            orderHistory.get(i).printSummary();
        }
    }

    public void viewLastReceipt() {
        if (orderHistory.isEmpty()) {
            System.out.println("No orders yet!");
            return;
        }
        orderHistory.get(orderHistory.size() - 1).printReceipt();
    }

    public void upgradeMembership() {
        if (membership == MembershipType.NONE && balance >= 100) {
            membership = MembershipType.SILVER;
            System.out.println("Congratulations! Upgraded to SILVER membership (10% discount)!");
        } else if (membership == MembershipType.SILVER && balance >= 300) {
            membership = MembershipType.GOLD;
            System.out.println("Congratulations! Upgraded to GOLD membership (20% discount)!");
        } else if (membership == MembershipType.GOLD && balance >= 500) {
            membership = MembershipType.PLATINUM;
            System.out.println("Congratulations! Upgraded to PLATINUM membership (30% discount)!");
        }
    }

    @Override
    public void showInfo() {
        System.out.printf("[CUSTOMER] %s | Phone: %s | Membership: %s | Balance: $%.2f\n",
                         name, phone, membership, balance);
    }
}