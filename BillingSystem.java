import java.util.List;

/**
 * BillingSystem - Singleton Pattern + Strategy Pattern
 * 
 * This class manages bill generation and printing for the restaurant system.
 * It uses two design patterns:
 * 
 * Design Patterns:
 * 1. Singleton Pattern: Ensures only one instance of BillingSystem exists
 *    - Private constructor prevents external instantiation
 *    - Static instance provides global access point
 *    - getInstance() method returns the single instance
 * 
 * 2. Strategy Pattern: Uses BillPrinter interface for different printing strategies
 *    - Context: This class uses BillPrinter implementations
 *    - Strategy: ConsoleBillPrinter and other printer implementations
 */

public class BillingSystem {
    // Singleton instance 
    private static final BillingSystem instance = new BillingSystem();
    
    // Bill generator for creating bill objects
    private final BillGenerator generator;
    // Bill printer strategy (can be changed at runtime)
    private BillPrinter printer;

    /**
     * Private constructor for Singleton Pattern
     * 
     * Prevents external instantiation. Initializes default bill generator
     * and printer (ConsoleBillPrinter).
     */
    private BillingSystem() {
        this.generator = new BillGenerator();
        this.printer = new ConsoleBillPrinter();
    }

    /**
     * provides the global access point to the single BillingSystem instance.
     */
    public static BillingSystem getInstance() {
        return instance;
    }

    /**
     * Generate and print a bill for an order
     * The printer can be changed at runtime (Strategy Pattern)
     */
    public void generateAndPrintBill(int orderId, List<OrderItem> items, OrderCalculator calculator) {
        Bill bill = generator.generateBill(orderId, items, calculator);
        // Print bill using configured printer strategy
        if (printer != null) {
            printer.print(bill);
        } else {
            System.out.println("No printer configured for bill printing");
        }
    }
}
