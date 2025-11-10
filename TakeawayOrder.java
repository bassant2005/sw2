public class TakeawayOrder extends OrderTemplate {
    private final String pickupTime;
    double subtotal = 0;

    public TakeawayOrder(PaymentHandler paymentHandler, OrderNotifier notifier, OrderCalculator calculator, String pickupTime) {
        super(paymentHandler, notifier, calculator);
        this.pickupTime = pickupTime;
    }

    @Override
    protected void calculateTotal() {
        subtotal = calculator.calculateSubtotal(items);
        double discount = calculator.calculateDiscount(items);
        double afterDiscount = Math.max(0.0, subtotal - discount);
        double tax = calculator.calculateTax(afterDiscount);
        double total = afterDiscount + tax;
        System.out.printf("[TakeawayOrder #%d] Subtotal=%.2f Discount=%.2f Tax=%.2f => Total=%.2f (Pickup=%s)\n", 
            getOrderId(), subtotal, discount, tax, total, pickupTime);
    }

    @Override
    protected boolean handlePayment() {
        if (paymentStrategy == null) return true;
        return paymentHandler.processPayment(calculator.calculateTotal(items), paymentStrategy);
    }

    @Override
    protected void notifySystems() {
        notifier.notifyObservers(this);
    }

    @Override
    protected void printBill() {
        BillingSystem.getInstance().generateAndPrintBill(getOrderId(), items, calculator);
        System.out.println("Pickup Time: " + pickupTime);
    }
}