package main;
import controller.FoodShopController;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FoodShopController controller = new FoodShopController("CADT Digital Canteen", "Phnom Penh");

        System.out.println("========================================");
        System.out.println("   WELCOME TO " + controller.getShopName());
        System.out.println("   Address: " + controller.getAddress());
        System.out.println("========================================");
        System.out.println(controller.getLastMessage());

        int choice;
        do {
            if (!controller.isLoggedIn()) {
                printMainMenu();
                System.out.print("Choose: ");
                choice = getIntInput(scanner);
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Username/Phone: ");
                        String username = scanner.nextLine();
                        System.out.print("Password: ");
                        String password = scanner.nextLine();
                        controller.login(username, password);
                        System.out.println(controller.getLastMessage());
                        break;
                    case 2:
                        controller.viewMenu();
                        break;
                    case 3:
                        System.out.print("Enter your phone number: ");
                        String phone = scanner.nextLine();
                        System.out.print("Enter your name: ");
                        String name = scanner.nextLine();
                        controller.registerAsGuest(phone, name);
                        System.out.println(controller.getLastMessage());
                        break;
                    case 0:
                        System.out.println("Thank you for visiting " + controller.getShopName() + "!");
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } else {
                printUserMenu(controller);
                System.out.print("Choose: ");
                choice = getIntInput(scanner);
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        controller.viewMenu();
                        break;
                    case 2:
                        System.out.print("Enter Food Name: ");
                        String foodName = scanner.nextLine();
                        System.out.print("Enter Quantity: ");
                        int qty = getIntInput(scanner);
                        scanner.nextLine();
                        controller.addToCart(foodName, qty);
                        System.out.println(controller.getLastMessage());
                        break;
                    case 3:
                        controller.viewCart();
                        break;
                    case 4:
                        System.out.print("Enter Food Name to remove: ");
                        String removeItem = scanner.nextLine();
                        controller.removeFromCart(removeItem);
                        System.out.println(controller.getLastMessage());
                        break;
                    case 5:
                        System.out.print("Enter Food Name: ");
                        String updateItem = scanner.nextLine();
                        System.out.print("Enter new quantity: ");
                        int newQty = getIntInput(scanner);
                        scanner.nextLine();
                        controller.updateCartQuantity(updateItem, newQty);
                        System.out.println(controller.getLastMessage());
                        break;
                    case 6:
                        controller.placeOrder();
                        System.out.println(controller.getLastMessage());
                        break;
                    case 7:
                        controller.viewOrderHistory();
                        break;
                    case 8:
                        controller.viewLastReceipt();
                        break;
                    case 9:
                        System.out.print("Enter amount to add: $");
                        double amount = getDoubleInput(scanner);
                        scanner.nextLine();
                        controller.addBalance(amount);
                        System.out.println(controller.getLastMessage());
                        break;
                    case 10:
                        controller.checkBalance();
                        break;
                    case 11:
                        if (controller.isAdmin()) {
                            showAdminMenu(controller, scanner);
                        } else {
                            System.out.println("Access denied! Admin only.");
                        }
                        break;
                    case 12:
                        controller.logout();
                        System.out.println(controller.getLastMessage());
                        break;
                    case 0:
                        System.out.println("Thank you for visiting " + controller.getShopName() + "!");
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } while (choice != 0);

        scanner.close();
    }

    private static void printMainMenu() {
        System.out.println("\n========================================");
        System.out.println("        MAIN MENU (Not Logged In)");
        System.out.println("========================================");
        System.out.println("1) Login");
        System.out.println("2) View Menu");
        System.out.println("3) Register as New Customer");
        System.out.println("0) Exit");
        System.out.println("========================================");
    }

    private static void printUserMenu(FoodShopController controller) {
        System.out.println("\n========================================");
        System.out.println("           USER MENU (Logged In)");
        System.out.println("========================================");
        System.out.println("Logged in: " + controller.getCurrentUserName() +
                          " (" + controller.getUserType() + " | " + controller.getMembershipInfo() + ")");
        System.out.println("========================================");
        System.out.println("1) View Menu");
        System.out.println("2) Add Item to Cart");
        System.out.println("3) View Cart");
        System.out.println("4) Remove Item from Cart");
        System.out.println("5) Update Cart Quantity");
        System.out.println("6) Place Order");
        System.out.println("7) View Order History");
        System.out.println("8) View Last Receipt");
        System.out.println("9) Add Balance");
        System.out.println("10) Check Balance");
        System.out.println("11) Admin Panel");
        System.out.println("12) Logout");
        System.out.println("0) Exit");
        System.out.println("========================================");
    }

    private static void showAdminMenu(FoodShopController controller, Scanner scanner) {
        int adminChoice;
        do {
            System.out.println("\n========== ADMIN PANEL ==========");
            System.out.println("1) Add New Food Item");
            System.out.println("2) Update Food Price");
            System.out.println("3) Remove Food Item");
            System.out.println("4) Set Food Availability");
            System.out.println("5) View All Customers");
            System.out.println("6) View All Orders");
            System.out.println("7) View System Statistics");
            System.out.println("8) Create Staff Account");
            System.out.println("9) View All Staff");
            System.out.println("10) Update Staff Salary");
            System.out.println("0) Exit Admin Panel");
            System.out.println("==================================");
            System.out.print("Choose: ");
            adminChoice = getIntInput(scanner);
            scanner.nextLine();

            switch (adminChoice) {
                case 1:
                    System.out.print("Food Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Price: $");
                    double price = getDoubleInput(scanner);
                    scanner.nextLine();
                    System.out.print("Category (Main/Drink/Dessert/Snack): ");
                    String category = scanner.nextLine();
                    controller.addFoodItem(name, price, category);
                    System.out.println(controller.getLastMessage());
                    break;
                case 2:
                    System.out.print("Food Name: ");
                    String foodName = scanner.nextLine();
                    System.out.print("New Price: $");
                    double newPrice = getDoubleInput(scanner);
                    scanner.nextLine();
                    controller.updateFoodPrice(foodName, newPrice);
                    System.out.println(controller.getLastMessage());
                    break;
                case 3:
                    System.out.print("Food Name: ");
                    String removeFood = scanner.nextLine();
                    controller.removeFoodItem(removeFood);
                    System.out.println(controller.getLastMessage());
                    break;
                case 4:
                    System.out.print("Food Name: ");
                    String availFood = scanner.nextLine();
                    System.out.print("Available? (1=Yes, 0=No): ");
                    int avail = getIntInput(scanner);
                    scanner.nextLine();
                    controller.setFoodAvailability(availFood, avail == 1);
                    System.out.println(controller.getLastMessage());
                    break;
                case 5:
                    controller.viewAllCustomers();
                    break;
                case 6:
                    controller.viewAllOrders();
                    break;
                case 7:
                    controller.viewSystemStats();
                    break;
                case 8:
                    System.out.print("Staff Name: ");
                    String staffName = scanner.nextLine();
                    System.out.print("Username: ");
                    String username = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();
                    System.out.print("Role (Admin/Manager/Staff): ");
                    String role = scanner.nextLine();
                    System.out.print("Salary: $");
                    double salary = getDoubleInput(scanner);
                    scanner.nextLine();
                    controller.createStaff(staffName, username, password, role, salary);
                    System.out.println(controller.getLastMessage());
                    break;
                case 9:
                    controller.viewAllStaff();
                    break;
                case 10:
                    System.out.print("Staff Username: ");
                    String staffUser = scanner.nextLine();
                    System.out.print("New Salary: $");
                    double newSalary = getDoubleInput(scanner);
                    scanner.nextLine();
                    controller.updateStaffSalary(staffUser, newSalary);
                    System.out.println(controller.getLastMessage());
                    break;
            }
        } while (adminChoice != 0);
    }

    private static int getIntInput(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Please enter a number: ");
            scanner.next();
        }
        return scanner.nextInt();
    }

    private static double getDoubleInput(Scanner scanner) {
        while (!scanner.hasNextDouble()) {
            System.out.print("Invalid input. Please enter a valid number: ");
            scanner.next();
        }
        return scanner.nextDouble();
    }
}