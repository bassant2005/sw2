// ============================================================================
// Payment Strategy Pattern - Improved Version
// ============================================================================

/**
 * PaymentHandler - Context in Strategy Pattern
 * Uses PaymentStrategy without knowing concrete implementations
 * Single Responsibility: Coordinate payment processing
 * Dependency Inversion: Depends on PaymentStrategy abstraction
 */
public class PaymentHandler {
    public boolean processPayment(double amount, PaymentStrategy strategy) {
        if (strategy == null) {
            System.out.println("[PaymentHandler] Payment strategy is null");
            return false;
        }
        if (amount <= 0) {
            System.out.println("[PaymentHandler] Invalid payment amount: " + amount);
            return false;
        }
        return strategy.pay(amount);
    }

    public String getPaymentMethodName(PaymentStrategy strategy) {
        return strategy != null ? strategy.getPaymentMethodName() : "Unknown";
    }
}

