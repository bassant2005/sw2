/**
 * CashPayment - Concrete Strategy
 * 
 * This class implements the PaymentStrategy interface for cash payments.
 * It represents one of the concrete payment algorithms that can be used
 * interchangeably with other payment methods.
 * 
 * Design Pattern: Strategy Pattern
 */
public class CashPayment implements PaymentStrategy {
    private final String cashierId;

    public CashPayment(String cashierId) {
        if (cashierId == null || cashierId.trim().isEmpty()) {
            throw new IllegalArgumentException("Cashier ID cannot be null or empty");
        }
        this.cashierId = cashierId;
    }

    /**
     * Process a cash payment
     * 
     * This method implements the Strategy Pattern's payment algorithm for cash.
     * Cash payments are always successful (assuming valid amount).
     */
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

    /**
     * Helper method to format currency values
     */
    private String format(double val) {
        return String.format("%.2f", val);
    }
}

