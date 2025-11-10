public class RestaurantMain {
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("  Restaurant Ordering System");
        System.out.println("========================================\n");
        
        // Create menu with various items
        Menu menu = createMenu();
        
        // Create main controller with the menu
        MainController controller = new MainController(menu);
        
        // Start the interactive ordering system
        controller.runInteractive();
        
        System.out.println("\n========================================");
        System.out.println("  Thank you for using our system!");
        System.out.println("========================================");
    }
    
 
    private static Menu createMenu() {
        Menu menu = new Menu();
        
        // Pizza items
        menu.addItem(new PizzaItem("Margherita Pizza", 55.0));
        menu.addItem(new PizzaItem("Pepperoni Pizza", 75.0));
        menu.addItem(new PizzaItem("Italian Chicken Pizza", 80.0));
        menu.addItem(new PizzaItem("Vegetarian Pizza", 60.0));
        
        // Burger items
        menu.addItem(new BurgerItem("Classic Beef Burger", 70.0));
        menu.addItem(new BurgerItem("Chicken Burger", 65.0));
        menu.addItem(new BurgerItem("Veggie Burger", 45.0));
        menu.addItem(new BurgerItem("BBQ Burger", 75.0));
        
        // Meat items
        menu.addItem(new BurgerItem("Grilled Chicken", 85.0));
        menu.addItem(new BurgerItem("Beef Steak", 120.0));
        
        return menu;
    }
}

