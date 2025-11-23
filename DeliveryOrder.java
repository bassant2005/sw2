public class DeliveryOrder extends OrderTemplate {

    // Delivery-specific information
    private final String deliveryAddress;
    private final double deliveryFee = 25.0;

    public DeliveryOrder(PaymentHandler paymentHandler, OrderNotifier notifier,
                         OrderCalculator calculator, String deliveryAddress) {

        super(paymentHandler, notifier, calculator);
        this.deliveryAddress = deliveryAddress;
    }

    @Override
    protected boolean handlePayment() {
        // If no payment strategy, assume payment done
        if (paymentStrategy == null) return true;

        // Use Strategy Pattern to process payment
        return paymentHandler.processPayment(calculator.calculateTotal(items), paymentStrategy);
    }

    /// DELIVERY OVERRIDE — adds delivery fee + tax on total price
    @Override
    protected void calculateTotal() {
        double subtotal = calculator.calculateSubtotal(items);
        double discount = calculator.calculateDiscount(items);
        double afterDiscount = Math.max(0.0, subtotal - discount);

        // Tax includes delivery fee
        double tax = calculator.calculateTax(afterDiscount + deliveryFee);
        double total = afterDiscount + tax + deliveryFee;

        System.out.printf("[DeliveryOrder #%d] Subtotal=%.2f Discount=%.2f DeliveryFee=%.2f Tax=%.2f => Total=%.2f\n",
                getOrderId(), subtotal, discount, deliveryFee, tax, total);
    }

    @Override
    protected void notifySystems() {
        notifier.notifyObservers(this);
        System.out.println("[DeliveryOrder] Delivery address: " + deliveryAddress);
    }

    /// Prints bill + delivery info
    @Override
    protected void printBill() {
        BillingSystem.getInstance().generateAndPrintBill(getOrderId(), items, calculator);
        System.out.println("Delivery Address: " + deliveryAddress);
        System.out.println("Delivery Fee: " + String.format("%.2f", deliveryFee));
    }
}
