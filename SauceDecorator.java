public class SauceDecorator extends MenuItemDecorator {
    private final String sauceType;
    double extraPrice = 20;

    public SauceDecorator(MenuItem wrapped, String sauceType) {
        super(wrapped);
        this.sauceType = sauceType;
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription() + " + " + sauceType + " sauce";
    }

    @Override
    public double getPrice() {
        return wrapped.getPrice() + extraPrice;
    }
}
