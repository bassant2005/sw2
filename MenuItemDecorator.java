/// Decorator Base Class (Decorator Pattern)

public abstract class MenuItemDecorator implements MenuItem {
    protected final MenuItem wrapped; // Component being decorated

    public MenuItemDecorator(MenuItem wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public String getDescription() {
        return wrapped.getDescription();
    }

    @Override
    public double getPrice() {
        return wrapped.getPrice();
    }
}
