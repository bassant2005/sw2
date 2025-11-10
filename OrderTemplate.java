import java.util.*;

/// Template Method
public abstract class OrderTemplate {

    protected final int orderId = new Random().nextInt(100) + 1;
    protected final List<OrderItem> items = new ArrayList<>();
    protected PaymentHandler paymentHandler;
    protected OrderNotifier notifier;
    protected OrderCalculator calculator;
    protected OrderStatus status = OrderStatus.NEW;
    protected PaymentStrategy paymentStrategy;
    protected String customerName = "Guest";

    public OrderTemplate(PaymentHandler paymentHandler, OrderNotifier notifier, OrderCalculator calculator) {
        this.paymentHandler = paymentHandler;
        this.notifier = notifier;
        this.calculator = calculator;
    }

    public int getOrderId() { return orderId; }
    public OrderStatus getStatus() { return status; }

    public void addItem(OrderItem it) { items.add(it); }
    public void setPaymentStrategy(PaymentStrategy s) { paymentStrategy = s; }
    public void setCustomerName(String name) { this.customerName = name; }

    // Template method
    public final void processOrder() {
        status = OrderStatus.PLACED;
        System.out.println("\n[Order #" + orderId + "] Placed by " + customerName);
        notifySystems();
        calculateTotal();
        if (!handlePayment()) {
            status = OrderStatus.CANCELLED;
            System.out.println("[Order #" + orderId + "] Payment failed -> CANCELLED");
            return;
        }
        printBill();
        status = OrderStatus.COMPLETED;
        System.out.println("[Order #" + orderId + "] Completed!");
    }

    protected abstract void calculateTotal();
    protected abstract boolean handlePayment();
    protected abstract void notifySystems();
    protected abstract void printBill();
}