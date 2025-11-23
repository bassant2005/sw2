import java.util.*;

/// TEMPLATE METHOD PATTERN (Superclass)
/// ------------------------------------
/// This class defines the *template method* `processOrder()`
/// which outlines the general workflow for processing any type of order.
/// Subclasses (DeliveryOrder, DineInOrder, TakeawayOrder) override only
/// the specific variable steps while keeping the workflow the same.

public abstract class OrderTemplate {

    // Auto-generated order ID (1–100)
    protected final int orderId = new Random().nextInt(100) + 1;

    // List of order items
    protected final List<OrderItem> items = new ArrayList<>();

    protected PaymentHandler paymentHandler; // For Strategy Pattern
    protected OrderNotifier notifier;        // For Observer Pattern
    protected OrderCalculator calculator;    // For calculation logic

    // Order state
    protected OrderStatus status = OrderStatus.NEW;

    // Payment Strategy (CreditCard, Cash, etc.)
    protected PaymentStrategy paymentStrategy;

    // Customer name (optional)
    protected String customerName = "Guest";

    /// Constructor injects dependencies (Dependency Injection)
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


    /// TEMPLATE METHOD — final so subclasses cannot modify it.
    /// Defines the fixed workflow of every order type.
    public final void processOrder() {

        status = OrderStatus.PLACED;
        System.out.println("\n[Order #" + orderId + "] Placed by " + customerName);

        notifySystems();
        calculateTotal();
        handlePayment();
        printBill();

        status = OrderStatus.COMPLETED;
        System.out.println("[Order #" + orderId + "] Completed!");
    }

    /// ABSTRACT METHODS — implemented by subclasses
    protected abstract void calculateTotal();
    protected abstract void notifySystems();
    protected abstract void printBill();

    protected abstract boolean handlePayment(); // uses Strategy Pattern
}
