/// MenuItem Interface (Component in Decorator Pattern)
/// ---------------------------------------------------
/// This is the core component interface that both:
/// 1. Concrete items (Burger, Pizza, etc.)
/// 2. Decorators (Extra cheese, Sauce, etc.)
/// must implement.
///
/// It allows decorators to wrap items and extend functionality.

public interface MenuItem {

    // Returns the name of the item including any added decorators
    String getDescription();

    // Returns final price = base price + extra toppings from decorators
    double getPrice();
}
