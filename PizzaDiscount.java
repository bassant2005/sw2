import java.util.*;

public class PizzaDiscount implements DiscountStrategy {
    double percent = 10.0;

    @Override
    public double apply(List<OrderItem> items) {
        double pizzaSubtotal = 0.0;
        for (OrderItem it : items) {
            if (it.getMenuItem().getDescription().toLowerCase().contains("pizza")) {
                pizzaSubtotal += it.getSubtotal();
            }
        }

        return pizzaSubtotal * percent / 100.0;
    }
}