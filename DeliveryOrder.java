/**
 * DeliveryOrder - Concrete Template
 * 
 * This class extends OrderTemplate and implements the delivery-specific
 * behavior for orders. It overrides the template method's variable steps
 * while keeping the overall workflow defined in the parent class.
 * 
 * Design Pattern: Template Method Pattern
 * - Concrete Class: Implements abstract methods from OrderTemplate
 */
public class DeliveryOrder extends OrderTemplate {

    private final String deliveryAddress;
    private final double deliveryFee = 25.0;

    public DeliveryOrder(PaymentHandler paymentHandler, OrderNotifier notifier,
                         OrderCalculator calculator, String deliveryAddress) {

        super(paymentHandler, notifier, calculator);
        this.deliveryAddress = deliveryAddress;
    }

    /**
     * This method uses the PaymentHandler and PaymentStrategy to process
     * the payment. The specific payment method (cash, credit card, etc.)
     * is determined by the strategy set on the order.
     */
    @Override
    protected boolean handlePayment() {
        // If no payment strategy, assume payment done
        if (paymentStrategy == null) return true;

        // Use Strategy Pattern to process payment
        return paymentHandler.processPayment(calculator.calculateTotal(items), paymentStrategy);
    }

    /**
     * Calculate total for delivery order
     * This method overrides the abstract calculateTotal() from OrderTemplate.
     */
    @Override
    protected void calculateTotal() {
        double subtotal = calculator.calculateSubtotal(items);
        double discount = calculator.calculateDiscount(items);
        double afterDiscount = Math.max(0.0, subtotal - discount);

        // Tax includes delivery fee (tax calculated on discounted amount + delivery fee)
        double tax = calculator.calculateTax(afterDiscount + deliveryFee);
        double total = afterDiscount + tax + deliveryFee;

        System.out.printf("[DeliveryOrder #%d] Subtotal=%.2f Discount=%.2f DeliveryFee=%.2f Tax=%.2f => Total=%.2f\n",
                getOrderId(), subtotal, discount, deliveryFee, tax, total);
    }

    /**
     * Notify systems about the order
     * 
     * This method overrides notifySystems() from OrderTemplate.
     * It notifies all registered observers (Kitchen, Waiter, etc.)
     * and displays delivery-specific information.
     */
    @Override
    protected void notifySystems() {
        // Notify all observers (Observer Pattern)
        notifier.notifyObservers(this);
        System.out.println("[DeliveryOrder] Delivery address: " + deliveryAddress);
    }

    /**
     * Print bill with delivery information 
     */
    @Override
    protected void printBill() {
        // Use BillingSystem singleton to generate and print bill
        BillingSystem.getInstance().generateAndPrintBill(getOrderId(), items, calculator);
        System.out.println("Delivery Address: " + deliveryAddress);
        System.out.println("Delivery Fee: " + String.format("%.2f", deliveryFee));
    }
}
