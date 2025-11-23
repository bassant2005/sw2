/// Concrete Decorator: Extra Cheese
/// --------------------------------
/// Adds extra cheese to a menu item by:
/// - Extending its description
/// - Increasing its total price

public class ExtraCheeseDecorator extends MenuItemDecorator {

    // Additional cost of extra cheese
    double extraPrice = 25;

    public ExtraCheeseDecorator(MenuItem wrapped) {
        super(wrapped);
    }

    @Override
    public String getDescription() {
        // Extend description of the wrapped item
        return wrapped.getDescription() + " + Extra Cheese";
    }

    @Override
    public double getPrice() {
        // Add extra cheese cost to wrapped item price
        return wrapped.getPrice() + extraPrice;
    }
}
