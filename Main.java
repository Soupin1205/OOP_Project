class Main {
    public static void main(String[] args) {

        System.out.println("Welcome to Online Food Order System");
        System.out.println("================================================");

        // Create menu
        Menu menu = new Menu("Online Food Menu", 10);

        FoodItem burger = new FoodItem("Burger", 5.0, "Fast Food");
        FoodItem pizza = new FoodItem("Pizza", 8.0, "Fast Food");

        menu.addFood(burger);
        menu.addFood(pizza);

        // Show menu
        System.out.println("\nToday's Menu:");
        menu.printMenu();

        // Create customer
        Customer customer = new Customer("Dane", true);
        System.out.println("\nCustomer: " + customer.name);
        System.out.println("Membership: " + (customer.member ? "YES" : "NO"));

        // Add to cart
        System.out.println("\nAdding items to cart...");
        customer.cart.addItem(burger, 2);
        System.out.println("✓ Burger x2 added to cart");

        // Cart summary
        System.out.println("\nCart Summary:");
        System.out.println("Burger x2 -> $" + (burger.price * 2));
        System.out.println("Total: $" + customer.cart.totalPrice);

        // Place order
        System.out.println("\nPlacing order...");
        customer.placeOrder();
        System.out.println("Order created successfully!");

        // Receipt
        System.out.println("\n===============Order Receipt================");
        System.out.println("Customer: " + customer.name);
        System.out.println("Items:");
        System.out.println("- Burger x2   $" + burger.price + " each");
        System.out.println("Total Amount: $" + customer.order.getTotal());
        System.out.println("==============================================");

        // Menu price change (real-life scenario)
        System.out.println("\nMenu price update (System Admin):");
        burger.price = 7.0;
        System.out.println("Burger price changed to $" + burger.price);

        // Snapshot proof (real-life explanation)
        System.out.println("\nChecking previous order total...");
        System.out.println("Previous Order Total: $" + customer.order.getTotal());
        System.out.println("(Note: Order price did NOT change)");

        // Null safety demo (real-life)
        System.out.println("\nSearching for item: Fried Rice");
        FoodItem notFound = menu.findFoodByName("Fried Rice");
        if (notFound == null) {
            System.out.println("Result: Item not found");
        }

        System.out.println("===============================================");
        System.out.println("Thank you for using Online Food Order System!");
    }
}
