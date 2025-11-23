import java.util.List;

/// STRATEGY PATTERN (Interface)
/// ----------------------------
/// This interface defines a common method `apply()`
/// that each discount rule must implement.
/// Different discounts = different strategy implementations.
public interface DiscountStrategy {

    // Calculates the discount amount based on items in the order.
    double apply(List<OrderItem> items);
}
