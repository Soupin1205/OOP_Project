package model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.FoodShopController;

public class Staff extends User {
    private String username;
    private String role;
    private double salary;

    private static final Map<String, List<String>> rolePermissions = new HashMap<>();

    static {
        rolePermissions.put("Admin", Arrays.asList(
            FoodShopController.ADD_FOOD,
            FoodShopController.UPDATE_PRICE,
            FoodShopController.REMOVE_FOOD,
            FoodShopController.VIEW_ALL_CUSTOMERS,
            FoodShopController.VIEW_ALL_ORDERS,
            FoodShopController.CREATE_STAFF,
            FoodShopController.UPDATE_SALARY
        ));
        rolePermissions.put("Manager", Arrays.asList(
            FoodShopController.ADD_FOOD,
            FoodShopController.UPDATE_PRICE,
            FoodShopController.VIEW_ALL_CUSTOMERS,
            FoodShopController.VIEW_ALL_ORDERS
        ));
        rolePermissions.put("Staff", Arrays.asList(
            FoodShopController.VIEW_ALL_CUSTOMERS,
            FoodShopController.VIEW_ALL_ORDERS
        ));
    }

    public Staff(String staffId, String name, String username, String password, String role, double salary) {
        super(staffId, name, password);
        this.username = username;
        this.role = role;
        this.salary = salary;
    }

    public String getUsername() { return username; }
    public String getRole() { return role; }
    public String getStaffId() { return userId; }
    public double getSalary() { return salary; }

    public void setSalary(double salary) { this.salary = salary; }
    
    public boolean can(String action) {
        List<String> permissions = rolePermissions.get(role);
        return permissions != null && permissions.contains(action);
    }

    @Override
    public void showInfo() {
        System.out.printf("[STAFF] %s | Role: %s | Username: %s | Salary: $%.2f | Active: %s\n",
                         name, role, username, salary, active);
    }
}