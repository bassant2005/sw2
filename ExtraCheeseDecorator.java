/// Concrete Decorators

public class ExtraCheeseDecorator extends MenuItemDecorator {
    double extraPrice = 25;

    public ExtraCheeseDecorator(MenuItem wrapped) {
        super(wrapped);
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription() + " + Extra Cheese";
    }

    @Override
    public double getPrice() {
        return wrapped.getPrice() + extraPrice;
    }
}
