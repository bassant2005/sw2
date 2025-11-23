/// Decorator Base Class (Decorator Pattern)
/// ----------------------------------------
/// This class implements MenuItem so that
/// ALL decorators can be treated as MenuItem objects.
///
/// It wraps another MenuItem, allowing extra behavior to be added.

public abstract class MenuItemDecorator implements MenuItem {

    // The component being decorated (could be plain item or another decorator)
    protected final MenuItem wrapped;

    public MenuItemDecorator(MenuItem wrapped) {
        this.wrapped = wrapped;
    }

    // Default behavior: delegate to wrapped component
    @Override
    public String getDescription() {
        return wrapped.getDescription();
    }

    @Override
    public double getPrice() {
        return wrapped.getPrice();
    }
}
