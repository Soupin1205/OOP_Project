public class Admin extends User {

    public Admin(String name, boolean member) {
        super(name, member);
    }

    @Override
    public void showInfo() {
        System.out.println("Admin Name: " + name);
        System.out.println("System Administrator Access");
    }

    // Admin updates food price
    public void updateFoodPrice(FoodItem food, double newPrice) {
        food.setPrice(newPrice);
        System.out.println(food.getName() + " price updated to $" + newPrice);
    }

}