public class SauceDecorator extends MenuItemDecorator {
    double extraPrice = 20;

    public SauceDecorator(MenuItem wrapped) {
        super(wrapped);
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription() + " + sauce";
    }

    @Override
    public double getPrice() {
        return wrapped.getPrice() + extraPrice;
    }
}
