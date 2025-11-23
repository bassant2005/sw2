/// FACTORY: Non-Vegetarian Menu
/// -----------------------------
/// Produces meat-based food items like chicken pizzas and beef burgers.

public class NonVegMenuFactory extends MenuFactory {

    @Override
    public Menu createMenu() {

        // Create a non-veg menu
        Menu menu = new Menu();

        // Add chicken and beef items
        menu.addItem(new PizzaItem("Italian Chicken", 80.0));
        menu.addItem(new BurgerItem("Classic Beef", 70.0));

        return menu;
    }
}
