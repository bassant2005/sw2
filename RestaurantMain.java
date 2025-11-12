public class RestaurantMain {
    public static void main(String[] args) {
        UIController ui = new UIController();

        ui.showHeader("Restaurant Ordering System");
        int choice = ui.selectMenuType();

        // Create menu factory based on user choice
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
                ui.showMessage("Invalid choice! Defaulting to Veg Menu.");
                factory = new VegMenuFactory();
        }

        // Create the menu
        Menu menu = factory.createMenu();

        // Create controller and start
        MainController controller = new MainController(menu, ui);
        controller.runInteractive();

        ui.showFooter("Thank you for using our system!");
    }
}
