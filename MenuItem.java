/// MenuItem Interface (Component in Decorator Pattern)

public interface MenuItem {
    String getDescription();  // Returns item name + any added decorations
    double getPrice();        // Returns base price + decorator prices
}
