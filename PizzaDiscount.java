import java.util.*;

/// Concrete Strategy: Pizza discount
/// --------------------------------
/// This class gives a 2% discount on any item that contains
/// the word "pizza" in its description.
public class PizzaDiscount implements DiscountStrategy {

    // Pizza discount percentage
    double percent = 2.0;

    @Override
    public double apply(List<OrderItem> items) {

        // Total price of all pizza items
        double pizzaSubtotal = 0.0;

        // Scan order items
        for (OrderItem it : items) {

            // If the item description contains "pizza"
            if (it.getMenuItem()
                    .getDescription()
                    .toLowerCase()
                    .contains("pizza"))
            {
                // Add pizza subtotal
                pizzaSubtotal += it.getSubtotal();
            }
        }

        // Return 2% of pizza subtotal as discount
        return pizzaSubtotal * (percent / 100.0);
    }
}
