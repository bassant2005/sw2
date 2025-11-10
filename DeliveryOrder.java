public class DeliveryOrder extends OrderTemplate {
    private final String deliveryAddress;
    private final double deliveryFee;

    
    public DeliveryOrder(PaymentHandler paymentHandler, OrderNotifier notifier, OrderCalculator calculator, String deliveryAddress, double deliveryFee) {
        super(paymentHandler, notifier, calculator);
        this.deliveryAddress = deliveryAddress;
        this.deliveryFee = deliveryFee;
    }

    @Override
    protected void calculateTotal() {
        double subtotal = calculator.calculateSubtotal(items);
        double discount = calculator.calculateDiscount(items);
        double afterDiscount = Math.max(0.0, subtotal - discount);
        // Tax is calculated on (afterDiscount + deliveryFee)
        double tax = calculator.calculateTax(afterDiscount + deliveryFee);
        double total = afterDiscount + tax + deliveryFee;

        System.out.printf("[DeliveryOrder #%d] Subtotal=%.2f Discount=%.2f DeliveryFee=%.2f Tax=%.2f => Total=%.2f\n", getOrderId(), subtotal, discount, deliveryFee, tax, total);
    }

    @Override
    protected boolean handlePayment() {
        if (paymentStrategy == null) {
            return true; // No payment required
        }
        double orderTotal = calculator.calculateTotal(items);
        double totalWithDelivery = orderTotal + deliveryFee;
        return paymentHandler.processPayment(totalWithDelivery, paymentStrategy);
    }

    @Override
    protected void notifySystems() {
        notifier.notifyObservers(this);
        System.out.println("[DeliveryOrder] Delivery address: " + deliveryAddress);
    }

    @Override
    protected void printBill() {
        BillingSystem.getInstance().generateAndPrintBill(getOrderId(), items, calculator);
        System.out.println("Delivery Address: " + deliveryAddress);
        System.out.println("Delivery Fee: " + String.format("%.2f", deliveryFee));
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public double getDeliveryFee() {
        return deliveryFee;
    }
}