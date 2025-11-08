/// Concrete Menu Items (Leaf in Decorator Pattern)

public class PizzaItem implements MenuItem {
    //final : The value cannot be changed once assigned.
    private final String name;
    private final double basePrice;

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
