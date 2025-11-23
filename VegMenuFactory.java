/// FACTORY: Vegetarian Menu
/// -------------------------
/// Produces only vegetarian-friendly pizza and burger items.

public class VegMenuFactory extends MenuFactory {

    @Override
    public Menu createMenu() {

        // Create a new menu
        Menu menu = new Menu();

        // Add vegetarian items
        menu.addItem(new PizzaItem("Margherita", 55.0));
        menu.addItem(new BurgerItem("Veggie Classic", 45.0));

        return menu;
    }
}
