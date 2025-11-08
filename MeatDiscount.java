import java.util.*;

public class MeatDiscount implements DiscountStrategy {
    double percent = 25.0;
    double meatSubtotal = 0.0;

    @Override
    public double apply(List<OrderItem> items) {

        for (OrderItem it : items) {
            String desc = it.getMenuItem().getDescription().toLowerCase();
            if (desc.contains("meat") || desc.contains("beef") || desc.contains("chicken"))
                meatSubtotal += it.getSubtotal();
        }

        return meatSubtotal * percent / 100.0;
    }
}