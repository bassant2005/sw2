/// Concrete Menu Item (Leaf in Decorator Pattern)
/// ------------------------------------------------
/// Represents a basic pizza with no added toppings.

public class PizzaItem implements MenuItem {

    private final String name;      // Pizza item name
    private final double basePrice; // Base pizza price

    public PizzaItem(String name, double basePrice) {
        this.name = name;
        this.basePrice = basePrice;
    }

    @Override
    public String getDescription() {
        return name + " (Pizza)";
    }

    @Override
    public double getPrice() {
        return basePrice;
    }
}
