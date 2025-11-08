/// a factory

public class NonVegMenuFactory extends MenuFactory {
    @Override
    public Menu createMenu() {
        Menu menu = new Menu();
        menu.addItem(new PizzaItem("Italian Chicken", 80.0));
        menu.addItem(new BurgerItem("Classic Beef", 70.0));
        return menu;
    }
}