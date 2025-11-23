/// Concrete Decorator: Sauce
/// --------------------------
/// Adds extra sauce to a menu item by:
/// - Updating the description
/// - Adding extra cost

public class SauceDecorator extends MenuItemDecorator {

    // Sauce extra cost
    double extraPrice = 20;

    public SauceDecorator(MenuItem wrapped) {
        super(wrapped);
    }

    @Override
    public String getDescription() {
        // Extend description
        return wrapped.getDescription() + " + sauce";
    }

    @Override
    public double getPrice() {
        // Add sauce charge
        return wrapped.getPrice() + extraPrice;
    }
}
