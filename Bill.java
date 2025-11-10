import java.util.*;

public class Bill {
    private final int orderId;
    private final List<OrderItem> items;
    private final double subtotal;
    private final double discount;
    private final double tax;
    private final double total;

    public Bill(int orderId, List<OrderItem> items, double subtotal, double discount, double tax, double total) {
        this.orderId = orderId;
        this.items = new ArrayList<>(items);
        this.subtotal = subtotal;
        this.discount = discount;
        this.tax = tax;
        this.total = total;
    }

    public int getOrderId() {
        return orderId;
    }

    public List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getDiscount() {
        return discount;
    }

    public double getTax() {
        return tax;
    }

    public double getTotal() {
        return total;
    }
}