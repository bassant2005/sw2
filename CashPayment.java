public class CashPayment implements PaymentStrategy {
    private final String cashierId;

    public CashPayment(String cashierId) {
        if (cashierId == null || cashierId.trim().isEmpty()) {
            throw new IllegalArgumentException("Cashier ID cannot be null or empty");
        }
        this.cashierId = cashierId;
    }

    @Override
    public boolean pay(double amount) {
        if (amount <= 0) {
            System.out.println("[CashPayment] Invalid amount: " + amount);
            return false;
        }
        System.out.println("[CashPayment] Received cash payment of " + format(amount) 
            + " by cashier " + cashierId);
        return true;
    }

    @Override
    public String getPaymentMethodName() {
        return "Cash";
    }

    public String getCashierId() {
        return cashierId;
    }

    private String format(double val) {
        return String.format("%.2f", val);
    }
}

