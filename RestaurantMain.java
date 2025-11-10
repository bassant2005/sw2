import java.util.*;

public class RestaurantMain {
    public static void main(String[] args) {
        System.out.println("========================================");
        System.out.println("  Restaurant Ordering System");
        System.out.println("========================================\n");
        
        // Create menu with various items
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select Menu Type: ");
        System.out.println("1. Veg Menu");
        System.out.println("2. Non-Veg Menu");
        System.out.println("3. Kids Menu");

        int choice = scanner.nextInt();
        MenuFactory factory;

        switch (choice) {
            case 1:
                factory = new VegMenuFactory();
                break;
            case 2:
                factory = new NonVegMenuFactory();
                break;
            case 3:
                factory = new KidsMenuFactory();
                break;
            default:
                System.out.println("Invalid choice! Defaulting to Veg Menu.");
                factory = new VegMenuFactory();
        }

        // Create a menu using the chosen factory
        Menu menu = factory.createMenu();
        
        // Create main controller with the menu
        MainController controller = new MainController(menu);
        
        // Start the interactive ordering system
        controller.runInteractive();
        
        System.out.println("\n========================================");
        System.out.println("  Thank you for using our system!");
        System.out.println("========================================");
    }
}

