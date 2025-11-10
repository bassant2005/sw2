public class MainController {
    private final Menu menu;
    private final PaymentHandler paymentHandler = new PaymentHandler();
    private final OrderNotifier notificationController = new OrderNotifier();
    private final OrderCalculator calculator = new OrderCalculator(14.0); // 14% tax
    private final UIController ui = new UIController();

    public MainController(Menu menu) {
        if (menu == null) {
            throw new IllegalArgumentException("Menu cannot be null");
        }
        this.menu = menu;
        
        // Set up notification system once when restaurant opens
        setupNotificationSystem();
    }

    private void setupNotificationSystem() {
        // Set up multiple Kitchen stations
        // Each kitchen station handles different types of orders
        Kitchen mainKitchen = new Kitchen("K1", "Main Kitchen");
        Kitchen pizzaStation = new Kitchen("K2", "Pizza Station");
        Kitchen grillStation = new Kitchen("K3", "Grill Station");
        
        notificationController.registerObserver(mainKitchen);
        notificationController.registerObserver(pizzaStation);
        notificationController.registerObserver(grillStation);
        
        // Set up multiple Waiters, each assigned to different table sections
        Waiter waiter1 = new Waiter("W1", 1); 
        Waiter waiter2 = new Waiter("W2", 6); 
        Waiter waiter3 = new Waiter("W3", 11); 
        Waiter deliveryWaiter = new Waiter("W4", null); // Handles delivery orders
        
        notificationController.registerObserver(waiter1);
        notificationController.registerObserver(waiter2);
        notificationController.registerObserver(waiter3);
        notificationController.registerObserver(deliveryWaiter);
        
        System.out.println("[System] Notification system ready!");
        System.out.println("  - 3 Kitchen stations subscribed (Main, Pizza, Grill)");
        System.out.println("  - 4 Waiters subscribed (covering all tables + delivery)");
        System.out.println("  - All will receive automatic notifications for new orders");
    }

    public void runInteractive() {
        System.out.println("Welcome to the Restaurant Ordering System!");

        // Get customer information
        String customerName = ui.readString("Enter customer name");
        String orderType = ui.readString("Enter order type (dinein/takeaway)").toLowerCase();

        // Create order
        OrderTemplate order = createOrder(orderType, paymentHandler, notificationController, calculator);
        order.setCustomerName(customerName);

        // Display menu and collect items
        ui.displayMenu(menu);
        collectOrderItems(order);

        // Configure discounts
        configureDiscounts();

        // Process payment
        PaymentStrategy paymentStrategy = selectPaymentMethod();
        order.setPaymentStrategy(paymentStrategy);

        // Kitchen and Waiter will be automatically notified (they're already subscribed)
        order.processOrder();
    }

    private OrderTemplate createOrder(String type, PaymentHandler handler, OrderNotifier notifier, 
                                     OrderCalculator calc) {
        switch (type.toLowerCase()) {
            case "dinein":
                return new DineInOrder(handler, notifier, calc);
            case "takeaway":
                return new TakeawayOrder(handler, notifier, calc);
            default:
                throw new IllegalArgumentException("Invalid order type");
        }
    }

    private void collectOrderItems(OrderTemplate order) {
        while (true) {
            String itemName = ui.readString("Enter menu item name to add (or 'done')");
            if (itemName.equalsIgnoreCase("done")) {
                break;
            }

            // Find item in menu
            MenuItem item = findMenuItemByName(itemName);
            if (item == null) {
                System.out.println("Item not found!");
                continue;
            }

            int qty = ui.readInt("Enter quantity");

            // Apply decorators
            String cheese = ui.readString("Add extra cheese? (yes/no)");
            if (cheese.equalsIgnoreCase("yes")) {
                item = new ExtraCheeseDecorator(item);
            }

            String sauce = ui.readString("Add sauce? (yes/no)");
            if (!sauce.equalsIgnoreCase("no")) {
                item = new SauceDecorator(item);
            }

            order.addItem(new OrderItem(item, qty));
        }
    }

    private MenuItem findMenuItemByName(String namePart) {
        for (MenuItem item : menu.getItems()) {
            if (item.getDescription().toLowerCase().contains(namePart.toLowerCase())) {
                return item;
            }
        }
        return null;
    }

    private void configureDiscounts() {
        // Pizza discount (10% off pizzas)
        PizzaDiscount pizzaDiscount = new PizzaDiscount();
        calculator.addDiscountStrategy(pizzaDiscount);

        // Meat discount (25% off meat items)
        MeatDiscount meatDiscount = new MeatDiscount();
        calculator.addDiscountStrategy(meatDiscount);
    }

    private PaymentStrategy selectPaymentMethod() {
        System.out.println("\nSelect payment method:\n1) Cash  2) Credit Card  3) Mobile Wallet");
        int payOption = ui.readInt("Enter choice");
        
        PaymentStrategy strategy;
        switch (payOption) {
            case 1:
                strategy = new CashPayment(ui.readString("Enter cashier ID"));
                break;
            case 2:
                strategy = new CreditCardPayment(
                    ui.readString("Enter card number"),
                    ui.readString("Enter auth code")
                );
                break;
            case 3:
                strategy = new MobileWalletPayment(ui.readString("Enter wallet ID"));
                break;
            default:
                System.out.println("Invalid, defaulting to cash.");
                strategy = new CashPayment("Default");
        }
        return strategy;
    }
}
