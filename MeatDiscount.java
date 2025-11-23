import java.util.*;

/// Concrete Strategy: Meat/Chicken/Beef discount
/// ---------------------------------------------
/// This class gives a 5% discount on any item whose
/// description includes "meat", "beef", or "chicken".
public class MeatDiscount implements DiscountStrategy {

    @Override
    public double apply(List<OrderItem> items) {
        // Discount percentage
        double percent = 5.0;

        // Used to accumulate the subtotal of meat-related items
        double meatSubtotal = 0.0;

        // Loop through each item in the order
        for (OrderItem it : items) {

            // Get the item description in lowercase for easy matching
            String desc = it.getMenuItem()
                    .getDescription()
                    .toLowerCase();

            // Check if the item is a meat-based product
            if (desc.contains("meat") ||
                    desc.contains("beef") ||
                    desc.contains("chicken"))
            {
                // Add subtotal of this item to the meat subtotal
                meatSubtotal += it.getSubtotal();
            }
        }

        // Apply 5% discount to all meat items combined
        return meatSubtotal * (percent / 100.0);
    }
}
