// Main class to run the program
public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Online Food Order System");
        System.out.println("================================================");

        // Create Menu
        Menu menu = new Menu("Online Food Menu", 10);

        // Create Food Items
        FoodItem burger = new FoodItem("Burger", 5.0, "Fast Food");
        FoodItem pizza = new FoodItem("Pizza", 8.0, "Fast Food");

        menu.addFood(burger);
        menu.addFood(pizza);

        // Show Menu
        System.out.println("\nToday's Menu:");
        menu.printMenu();

        // Create Customer
        Customer customer = new Customer("Dane", true);
        customer.showInfo();

        // Add to cart
        System.out.println("\nAdding items to cart...");
        customer.getCart().addItem(burger, 2);
        System.out.println("✓ Burger x2 added to cart");

        // Cart Summary
        System.out.println("\nCart Summary:");
        System.out.println("Burger x2 -> $" + (burger.getPrice() * 2));
        System.out.println("Total: $" + customer.getCart().getTotalPrice());

        // Place Order
        System.out.println("\nPlacing order...");
        customer.placeOrder();

        // Print Receipt
        System.out.println("\n===============Order Receipt================");
        System.out.println("Customer: " + customer.getName());
        System.out.println("Items:");
        System.out.println("- Burger x2   $" + burger.getPrice() + " each");
        System.out.println("Total Amount: $" + customer.getOrder().getTotal());
        System.out.println("============================================");

        // Price change example
        System.out.println("\nMenu price update (System Admin):");
        burger.setPrice(7.0);
        System.out.println("Burger price changed to $" + burger.getPrice());

        // Previous order total remains unchanged
        System.out.println("\nPrevious Order Total: $" + customer.getOrder().getTotal());

        // Null safety demo
        System.out.println("\nSearching for item: Fried Rice");
        FoodItem notFound = menu.findFoodByName("Fried Rice");
        if (notFound == null) {
            System.out.println("Result: Item not found");
        }

        System.out.println("===============================================");
        System.out.println("Thank you for using Online Food Order System!");
    }
}
