import java.util.*;

/**
 * Bill - Data Transfer Object for billing information
 * 
 * This class represents a bill/invoice for an order. It's an immutable
 * data structure that holds all financial information about an order.
 * 
 * Design Pattern: Part of Billing System (used with Singleton and Strategy patterns)
 */
public class Bill {
    // Order identifier
    private final int orderId;
    // List of items in the order (defensive copy for immutability)
    private final List<OrderItem> items;
    // Financial values (all final for immutability)
    private final double subtotal;
    private final double discount;
    private final double tax;
    private final double total;

    /**
     * Constructor creates an immutable Bill object
     */
    public Bill(int orderId, List<OrderItem> items, double subtotal, double discount, double tax, double total) {
        this.orderId = orderId;
        // Create defensive copy to ensure immutability
        this.items = new ArrayList<>(items);
        this.subtotal = subtotal;
        this.discount = discount;
        this.tax = tax;
        this.total = total;
    }

    public int getOrderId() {
        return orderId;
    }

    /**
     * Get the list of order items
     */
    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    /**
     * Get the subtotal (before discounts and tax)
     */
    public double getSubtotal() {
        return subtotal;
    }

    public double getDiscount() {
        return discount;
    }

    public double getTax() {
        return tax;
    }

    /**
     * Total amount (subtotal - discount + tax)
     */
    public double getTotal() {
        return total;
    }
}