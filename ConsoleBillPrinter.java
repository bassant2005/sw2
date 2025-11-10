public class ConsoleBillPrinter implements BillPrinter {
    @Override
    public void print(Bill bill) {
        if (bill == null) {
            System.out.println("Bill is null");
            return;
        }

        System.out.println("------------------------------");
        System.out.println("BILL - Order #" + bill.getOrderId());
        System.out.println("------------------------------\n");

        for (OrderItem item : bill.getItems()) {
            System.out.printf("%-40s %8.2f\n", item.getDescription(), item.getSubtotal());
        }

        System.out.println("\n------------------------------");
        System.out.printf("%-40s %8.2f\n", "SUBTOTAL", bill.getSubtotal());
        System.out.printf("%-40s %8.2f\n", "DISCOUNT", bill.getDiscount());
        System.out.printf("%-40s %8.2f\n", "TAX", bill.getTax());
        System.out.println("\n------------------------------");
        System.out.printf("%-40s %8.2f\n", "TOTAL", bill.getTotal());
        System.out.println("\n------------------------------");
    }
}