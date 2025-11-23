/// Concrete Menu Item (Leaf in Decorator Pattern)
/// ----------------------------------------------
/// A plain base burger with no extra toppings.
/// Decorators will wrap around this object to extend functionality.

public class BurgerItem implements MenuItem {

    private final String name;      // Burger name
    private final double basePrice; // Base price without additions

    public BurgerItem(String name, double basePrice) {
        this.name = name;
        this.basePrice = basePrice;
    }

    @Override
    public String getDescription() {
        // Describes the item type
        return name + " (Burger)";
    }

    @Override
    public double getPrice() {
        // Base price — decorators will add on top of this
        return basePrice;
    }
}
