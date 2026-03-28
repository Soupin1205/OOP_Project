import src.models.Customer;
import src.models.FoodItem;
import src.service.Menu;

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
        System.out.printf("Burger x2 -> $%.2f\n", burger.getPrice() * 2);
        System.out.printf("Pizza x1 -> $%.2f\n", pizza.getPrice());
        System.out.printf("Total Cart: $%.2f\n", customer.getCart().getTotalPrice());

        // Place Order (snapshot of current prices)
        System.out.println("\nPlacing order...");
        customer.placeOrder();

        // Print Receipt (snapshot)
        System.out.println("\n===============Order Receipt================");
        System.out.println("Customer: " + customer.getName());
        System.out.println("Items:");
        for (int i = 0; i < customer.getOrder().getItemCount(); i++) {
            System.out.printf("- %-10s x%-2d $%.2f each\n",
                    customer.getOrder().getItemNames()[i],
                    customer.getOrder().getQtySnapshot()[i],
                    customer.getOrder().getPriceSnapshot()[i]);
        }
        System.out.printf("Total Amount: $%.2f\n", customer.getOrder().getTotal());
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
         for (int i = 0; i < customer.getOrder().getItemCount(); i++) {
            String name = customer.getOrder().getItemNames()[i];
            int qty = customer.getOrder().getQtySnapshot()[i];
            double oldPrice = customer.getOrder().getPriceSnapshot()[i];

            // Get current price from menu
            FoodItem food = menu.findFoodByName(name);
            double newPrice = (food != null) ? food.getPrice() : oldPrice;

            System.out.printf("%-10s %-5d $%-9.2f $%-9.2f $%-9.2f $%-9.2f\n",
                    name, qty, oldPrice, newPrice, oldPrice * qty, newPrice * qty);
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