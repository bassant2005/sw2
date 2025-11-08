class DineInOrder extends OrderTemplate {
    double subtotal = 0;

    @Override
    protected void calculateTotal() {
        for (OrderItem it : items) subtotal += it.getSubtotal();
        double tax = subtotal * 0.14; // 14% tax
        double total = subtotal + tax;
        System.out.printf("[DineInOrder #%d] Subtotal=%.2f Tax=%.2f => Total=%.2f\n", orderId, subtotal, tax, total);
    }

    @Override
    protected boolean handlePayment() {
        if (paymentStrategy == null) return true;
        double total = items.stream().mapToDouble(OrderItem::getSubtotal).sum() * 1.14;
        return paymentStrategy.pay(total);
    }

    @Override
    protected void notifySystems() {
        System.out.println("[DineInOrder #" + orderId + "] Notifying Kitchen and Waiter...");
    }

    @Override
    protected void printBill() {
        System.out.println("--- BILL ---");
        for (OrderItem it : items) System.out.printf("%-30s %6.2f\n", it.getDescription(), it.getSubtotal());
    }
}