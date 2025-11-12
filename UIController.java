import java.util.*;

public class UIController {
    private final Scanner sc = new Scanner(System.in);

    // ===== Basic Input Methods =====
    public int readInt(String prompt) {
        while (true) {
            System.out.print(prompt + ": ");
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid number!");
            }
        }
    }

    public String readString(String prompt) {
        System.out.print(prompt + ": ");
        return sc.nextLine();
    }

    // ===== General Output =====
    public void showMessage(String msg) {
        System.out.println(msg);
    }

    public void showHeader(String title) {
        System.out.println("========================================");
        System.out.println("  " + title);
        System.out.println("========================================\n");
    }

    public void showFooter(String message) {
        System.out.println("\n========================================");
        System.out.println("  " + message);
        System.out.println("========================================");
    }

    // ===== Menu Selection =====
    public int selectMenuType() {
        System.out.println("Select Menu Type: ");
        System.out.println("1. Veg Menu");
        System.out.println("2. Non-Veg Menu");
        System.out.println("3. Kids Menu");
        return readInt("Enter choice");
    }

    public void displayMenu(Menu menu) {
        System.out.println("\n=== MENU ===");
        int idx = 1;
        for (MenuItem item : menu.getItems()) {
            System.out.printf("%d) %-30s -> %.2f\n", idx++, item.getDescription(), item.getPrice());
        }
    }

    // ===== Order Setup =====
    public void showWelcomeMessage() {
        System.out.println("Welcome to the Restaurant Ordering System!");
    }

    public String readOrderType() {
        while (true) {
            String type = readString("Enter order type (dinein/takeaway)").toLowerCase();
            if (type.equals("dinein") || type.equals("takeaway")) return type;
            System.out.println("Invalid type! Please enter dinein or takeaway.");
        }
    }

    // ===== Notification Setup =====
    public void showNotificationSystemSetup() {
        System.out.println("[System] Notification system ready!");
        System.out.println("  - 3 Kitchen stations subscribed (Main, Pizza, Grill)");
        System.out.println("  - 4 Waiters subscribed (covering all tables + delivery)");
        System.out.println("  - All will receive automatic notifications for new orders");
    }

    // ===== Decorators =====
    public MenuItem configureExtras(MenuItem item) {
        String cheese = readString("Add extra cheese? (yes/no)");
        if (cheese.equalsIgnoreCase("yes")) item = new ExtraCheeseDecorator(item);

        String sauce = readString("Add sauce? (yes/no)");
        if (!sauce.equalsIgnoreCase("no")) item = new SauceDecorator(item);

        return item;
    }

    // ===== Payment =====
    public PaymentStrategy selectPaymentMethod() {
        System.out.println("\nSelect payment method:\n1) Cash  2) Credit Card  3) Mobile Wallet");
        int payOption = readInt("Enter choice");

        switch (payOption) {
            case 1:
                return new CashPayment(readString("Enter cashier ID"));
            case 2:
                return new CreditCardPayment(
                        readString("Enter card number"),
                        readString("Enter auth code")
                );
            case 3:
                return new MobileWalletPayment(readString("Enter wallet ID"));
            default:
                System.out.println("Invalid, defaulting to cash.");
                return new CashPayment("Default");
        }
    }
}
