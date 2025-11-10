public class MobileWalletPayment implements PaymentStrategy {
    private final String walletId;

    public MobileWalletPayment(String walletId) {
        if (walletId == null || walletId.trim().isEmpty()) {
            throw new IllegalArgumentException("Wallet ID cannot be null or empty");
        }
        this.walletId = walletId;
    }

    @Override
    public boolean pay(double amount) {
        if (amount <= 0) {
            System.out.println("[MobileWalletPayment] Invalid amount: " + amount);
            return false;
        }
        System.out.println("[MobileWalletPayment] Calling wallet gateway for " + format(amount) 
            + " wallet=" + walletId);
        // Simulate 90% success rate
        return Math.random() > 0.1;
    }

    @Override
    public String getPaymentMethodName() {
        return "Mobile Wallet";
    }

    public String getWalletId() {
        return walletId;
    }

    private String format(double val) {
        return String.format("%.2f", val);
    }
}

