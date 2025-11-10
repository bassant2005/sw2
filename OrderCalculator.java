import java.util.*;

public class OrderCalculator {
    private final double taxPercent;
    private final List<DiscountStrategy> discountStrategies;

    public OrderCalculator(double taxPercent) {
        if (taxPercent < 0 || taxPercent > 100) {
            throw new IllegalArgumentException("Tax percentage must be between 0 and 100");
        }
        this.taxPercent = taxPercent;
        this.discountStrategies = new ArrayList<>();
    }

    public void addDiscountStrategy(DiscountStrategy strategy) {
        if (strategy != null && !discountStrategies.contains(strategy)) {
            discountStrategies.add(strategy);
        }
    }

    public void removeDiscountStrategy(DiscountStrategy strategy) {
        discountStrategies.remove(strategy);
    }

    public void clearDiscountStrategies() {
        discountStrategies.clear();
    }

    public double calculateSubtotal(List<OrderItem> items) {
        if (items == null || items.isEmpty()) {
            return 0.0;
        }
        return items.stream().mapToDouble(OrderItem::getSubtotal).sum();
    }

    public double calculateDiscount(List<OrderItem> items) {
        if (items == null || items.isEmpty() || discountStrategies.isEmpty()) {
            return 0.0;
        }
        return discountStrategies.stream().mapToDouble(strategy -> strategy.apply(items)).sum();
    }

    public double calculateTax(double amountAfterDiscount) {
        if (amountAfterDiscount < 0) {
            return 0.0;
        }
        return amountAfterDiscount * taxPercent / 100.0;
    }

    public double calculateTotal(List<OrderItem> items) {
        double subtotal = calculateSubtotal(items);
        double discount = calculateDiscount(items);
        double afterDiscount = Math.max(0.0, subtotal - discount);
        double tax = calculateTax(afterDiscount);
        return afterDiscount + tax;
    }

    public double getTaxPercent() {
        return taxPercent;
    }

    public int getDiscountStrategyCount() {
        return discountStrategies.size();
    }
}

