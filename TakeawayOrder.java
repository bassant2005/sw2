class TakeawayOrder extends OrderTemplate {
    private final String pickupTime;
    public TakeawayOrder(String pickupTime) { this.pickupTime = pickupTime; }
    double subtotal = 0;

    @Override
    protected void calculateTotal() {
        for (OrderItem it : items) subtotal += it.getSubtotal();
        double tax = subtotal * 0.14;
        double total = subtotal + tax;
        System.out.printf("[TakeawayOrder #%d] Subtotal=%.2f Tax=%.2f => Total=%.2f (Pickup=%s)\n", orderId, subtotal, tax, total, pickupTime);
    }

    @Override
    protected boolean handlePayment() {
        if (paymentStrategy == null) return true;
        double total = items.stream().mapToDouble(OrderItem::getSubtotal).sum() * 1.14;
        return paymentStrategy.pay(total);
    }

    @Override
    protected void notifySystems() {
        System.out.println("[TakeawayOrder #" + orderId + "] Notifying Kitchen...");
    }

    @Override
    protected void printBill() {
        System.out.println("--- BILL ---");
        for (OrderItem it : items) System.out.printf("%-30s %6.2f\n", it.getDescription(), it.getSubtotal());
        System.out.println("Pickup Time: " + pickupTime);
    }
}