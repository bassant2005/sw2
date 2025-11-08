import java.util.*;

/// Template Method
abstract class OrderTemplate {
    protected final int orderId;
    protected final List<OrderItem> items = new ArrayList<>();
    protected PaymentStrategy paymentStrategy;
    protected String customerName = "Guest";

    public OrderTemplate() { this.orderId = new Random().nextInt(100) + 1; }

    public void addItem(OrderItem it) { items.add(it); }
    public void setPaymentStrategy(PaymentStrategy s) { paymentStrategy = s; }
    public void setCustomerName(String name) { this.customerName = name; }

    // Template method
    public final void processOrder() {
        System.out.println("\n[Order #" + orderId + "] Placed by " + customerName);
        notifySystems();
        calculateTotal();
        if (!handlePayment()) {
            System.out.println("[Order #" + orderId + "] Payment failed -> CANCELLED");
            return;
        }
        printBill();
        System.out.println("[Order #" + orderId + "] Completed!");
    }

    protected abstract void calculateTotal();
    protected abstract boolean handlePayment();
    protected abstract void notifySystems();
    protected abstract void printBill();
}