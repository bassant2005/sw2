/// Concrete Menu Items (Leaf in Decorator Pattern)

public class BurgerItem implements MenuItem {
    private final String name;
    private final double basePrice;

    public BurgerItem(String name, double basePrice) {
        this.name = name;
        this.basePrice = basePrice;
    }

    @Override
    public String getDescription() {
        return name + " (Burger)";
    }

    @Override
    public double getPrice() {
        return basePrice;
    }
}