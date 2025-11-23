/// FACTORY: Kids Menu
/// -------------------
/// Creates a special menu for children containing smaller portions.
/// This is one "family" of related products in the Abstract Factory system.

public class KidsMenuFactory extends MenuFactory {

    @Override
    public Menu createMenu() {

        // Create an empty menu object
        Menu menu = new Menu();

        // Add kids-friendly items
        menu.addItem(new PizzaItem("Kids Small Margherita", 30.0));
        menu.addItem(new BurgerItem("Kids Mini Burger", 25.0));

        return menu;
    }
}
