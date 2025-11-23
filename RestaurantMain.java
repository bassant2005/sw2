public class RestaurantMain {
    public static void main(String[] args) {
        UIController ui = new UIController();

        ui.showMessage("Restaurant Ordering System");

        // Create controller and start
        MainController controller = new MainController(ui.MenuType(), ui);
        controller.runInteractive();

        ui.showMessage("Thank you for using our system!");
    }
}
