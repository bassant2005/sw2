import java.util.List;

/**
 * BillGenerator - Part of Billing System
 * 
 * This class is responsible for creating Bill objects from order data.
 * It uses OrderCalculator to compute all financial values (subtotal, discount, tax, total).
 * 
 * Design Pattern: Part of Strategy Pattern (used by BillingSystem)
 */
public class BillGenerator {
    /**
     * Generate a Bill object from order information
     * 
     * This method creates a complete Bill object by:
     * 1. Validating inputs
     * 2. Calculating subtotal using OrderCalculator
     * 3. Calculating discounts using OrderCalculator
     * 4. Calculating tax on discounted amount
     * 5. Creating and returning a Bill object
     */
    public Bill generateBill(int orderId, List<OrderItem> items, OrderCalculator calculator) {
        if (calculator == null) {
            throw new IllegalArgumentException("Calculator cannot be null");
        }
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Items list cannot be null or empty");
        }

        // Calculate all financial components using OrderCalculator
        double subtotal = calculator.calculateSubtotal(items);
        double discount = calculator.calculateDiscount(items);
        double afterDiscount = subtotal - discount;
        double tax = calculator.calculateTax(afterDiscount);
        double total = afterDiscount + tax;

        // Create and return Bill object with all calculated values
        return new Bill(orderId, items, subtotal, discount, tax, total);
    }
}
