// ============================================================================
// Payment Strategy Pattern - Improved Version
// ============================================================================

/**
 * PaymentStrategy interface - Strategy Pattern
 * Defines contract for payment processing algorithms
 * Single Responsibility: Define payment algorithm contract
 */
public interface PaymentStrategy {
    boolean pay(double amount);
    String getPaymentMethodName();
}
