/// a factory

public class VegMenuFactory extends MenuFactory {
    @Override
    public Menu createMenu() {
        Menu menu = new Menu();
        menu.addItem(new PizzaItem("Margherita", 55.0));
        menu.addItem(new BurgerItem("Veggie Classic", 45.0));
        return menu;
    }
}