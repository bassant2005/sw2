/// a factory

public class KidsMenuFactory extends MenuFactory {
    @Override
    public Menu createMenu() {
        Menu menu = new Menu();
        menu.addItem(new PizzaItem("Kids Small Margherita", 30.0));
        menu.addItem(new BurgerItem("Kids Mini Burger", 25.0));
        return menu;
    }
}