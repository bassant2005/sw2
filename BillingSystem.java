import java.util.List;

public class BillingSystem {
    private static final BillingSystem instance = new BillingSystem();
    
    private final BillGenerator generator;
    private BillPrinter printer;

    private BillingSystem() {
        this.generator = new BillGenerator();
        this.printer = new ConsoleBillPrinter();
    }

    public static BillingSystem getInstance() {
        return instance;
    }

    public void setPrinter(BillPrinter printer) {
        this.printer = printer;
    }

    public BillPrinter getPrinter() {
        return printer;
    }

    public void generateAndPrintBill(int orderId, List<OrderItem> items, OrderCalculator calculator) {
        Bill bill = generator.generateBill(orderId, items, calculator);
        if (printer != null) {
            printer.print(bill);
        } else {
            System.out.println("No printer configured for bill printing");
        }
    }
}
