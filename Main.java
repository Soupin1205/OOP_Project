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
        customer.getCart().addItem(burger, 2); // 2 burgers
        customer.getCart().addItem(pizza);     // 1 pizza (overloaded method)
        System.out.println("✓ Burger x2 added to cart");
        System.out.println("✓ Pizza x1 added to cart");

        // Cart Summary before order
        System.out.println("\nCart Summary before placing order:");
        System.out.println("Burger x2 -> $" + (burger.getPrice() * 2));
        System.out.println("Pizza x1 -> $" + pizza.getPrice());
        System.out.println("Total Cart: $" + customer.getCart().getTotalPrice());

        // Place Order (snapshot of current prices)
        System.out.println("\nPlacing order...");
        customer.placeOrder();

        // Print Receipt (snapshot)
        System.out.println("\n===============Order Receipt================");
        System.out.println("Customer: " + customer.getName());
        System.out.println("Items:");
        System.out.println("- Burger x2   $" + burger.getPrice() + " each");
        System.out.println("- Pizza x1    $" + pizza.getPrice() + " each");
        System.out.println("Total Amount: $" + customer.getOrder().getTotal());
        System.out.println("============================================");

        // Price change example (Admin updates price)
        System.out.println("\nMenu price update (System Admin):");
        burger.setPrice(7.0);                     // normal price change
        pizza.setPrice(10.0, 2.0);                // apply discount (overloaded method)
        System.out.println("Burger price changed to $" + burger.getPrice());
        System.out.println("Pizza price updated to $" + pizza.getPrice() + " (after $2 discount)");

        // Print detailed table comparing old vs new prices
        System.out.println("\nCart & Order Summary After Price Update:");
        System.out.println("--------------------------------------------------------");
        System.out.printf("%-10s %-5s %-10s %-10s %-10s %-10s\n",
                "Item", "Qty", "Old Price", "New Price", "Old Total", "New Total");
        System.out.println("--------------------------------------------------------");

        FoodItem[] foods = customer.getCart().getFoods();
        int[] qtys = customer.getCart().getQuantities();

        for (int i = 0; i < customer.getCart().getCount(); i++) {
            double oldPrice = 0;
            // find old price from order snapshot
            for (int j = 0; j < customer.getOrder().getItemCount(); j++) {
                if (customer.getOrder().getItemNames()[j].equals(foods[i].getName())) {
                    oldPrice = customer.getOrder().getPriceSnapshot()[j];
                    break;
                }
            }
            double newPrice = foods[i].getPrice();
            int qty = qtys[i];
            System.out.printf("%-10s %-5d $%-9.2f $%-9.2f $%-9.2f $%-9.2f\n",
                    foods[i].getName(), qty, oldPrice, newPrice, oldPrice * qty, newPrice * qty);
        }

        System.out.println("--------------------------------------------------------");
        System.out.printf("Order Total (snapshot) = $%.2f\n", customer.getOrder().getTotal());
        System.out.printf("Cart Total (current prices) = $%.2f\n", customer.getCart().getTotalPrice());

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