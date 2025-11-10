import java.util.List;

public class BillGenerator {
    public Bill generateBill(int orderId, List<OrderItem> items, OrderCalculator calculator) {
        if (calculator == null) {
            throw new IllegalArgumentException("Calculator cannot be null");
        }
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Items list cannot be null or empty");
        }

        double subtotal = calculator.calculateSubtotal(items);
        double discount = calculator.calculateDiscount(items);
        double afterDiscount = subtotal - discount;
        double tax = calculator.calculateTax(afterDiscount);
        double total = afterDiscount + tax;

        return new Bill(orderId, items, subtotal, discount, tax, total);
    }
}
