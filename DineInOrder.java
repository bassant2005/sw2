public class DineInOrder extends OrderTemplate {
    private final String tableNumber;
    double subtotal = 0;

    public DineInOrder(PaymentHandler paymentHandler, OrderNotifier notifier, OrderCalculator calculator, String tableNumber) {
        super(paymentHandler, notifier, calculator);
        this.tableNumber = tableNumber;
    }

    @Override
    protected void calculateTotal() {
        subtotal = calculator.calculateSubtotal(items);
        double discount = calculator.calculateDiscount(items);
        double afterDiscount = Math.max(0.0, subtotal - discount);
        double tax = calculator.calculateTax(afterDiscount);
        double total = afterDiscount + tax;
        System.out.printf("[DineInOrder #%d] Subtotal=%.2f Discount=%.2f Tax=%.2f => Total=%.2f\n", 
            getOrderId(), subtotal, discount, tax, total);
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
    }
}