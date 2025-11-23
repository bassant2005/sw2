/**
 * CreditCardPayment - Concrete Strategy
 * 
 * This class implements the PaymentStrategy interface for credit card payments.
 * It represents the credit card payment algorithm that can be used
 * interchangeably with other payment methods.
 * 
 * Design Pattern: Strategy Pattern
 * - Concrete Strategy: Implements credit card payment processing algorithm
 */
public class CreditCardPayment implements PaymentStrategy {
    private final String cardNumber;
    private final String authorizationCode;

    public CreditCardPayment(String cardNumber, String authorizationCode) {
        if (cardNumber == null || cardNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Card number cannot be null or empty");
        }
        if (authorizationCode == null || authorizationCode.trim().isEmpty()) {
            throw new IllegalArgumentException("Authorization code cannot be null or empty");
        }
        this.cardNumber = maskCardNumber(cardNumber);
        this.authorizationCode = authorizationCode;
    }

    /**
     * Process a credit card payment
     * This method implements the Strategy Pattern's payment algorithm for credit cards.
     */
    @Override
    public boolean pay(double amount) {
        if (amount <= 0) {
            System.out.println("[CreditCardPayment] Invalid amount: " + amount);
            return false;
        }
        System.out.println("[CreditCardPayment] Simulating authorization for " + format(amount)
            + " using card " + cardNumber + " auth=" + authorizationCode);
        // Simulate 95% success rate
        return Math.random() > 0.05;
    }

    /**
     * Mask card number for security
     */
    private String maskCardNumber(String cardNumber) {
        if (cardNumber.length() <= 4) {
            return "****";
        }
        return "****" + cardNumber.substring(cardNumber.length() - 4);
    }

    private String format(double val) {
        return String.format("%.2f", val);
    }
}

