/**
 * MainController - Facade Pattern
 * 
 * This class acts as a facade that provides a simplified interface to the
 * complex restaurant ordering system. 
 * It hides the complexity of coordinating multiple subsystems 
 * (payment, notifications, calculations, orders) behind a single, easy-to-use interface.
 * 
 * Design Pattern: Facade Pattern
 * - Facade: This class provides a unified interface to subsystems
 * - Subsystems: PaymentHandler, OrderNotifier, OrderCalculator, OrderTemplate
 */
public class MainController {
    private final Menu menu;
    // Payment handler for processing payments (Strategy Pattern)
    private final PaymentHandler paymentHandler = new PaymentHandler();
    // Order notifier for observer notifications (Observer Pattern)
    private final OrderNotifier notificationController = new OrderNotifier();
    // Order calculator for financial calculations (Strategy Pattern for discounts)
    private final OrderCalculator calculator = new OrderCalculator(14.0); // 14% tax
    // UI controller for user interactions
    private final UIController ui;

    public MainController(Menu menu, UIController ui) {
        if (menu == null) throw new IllegalArgumentException("Menu cannot be null");
        this.menu = menu;
        this.ui = ui;
        setupNotificationSystem();
    }

    /**
     * Setup the notification system (Observer Pattern)
     * 
     * This method initializes the Observer Pattern by registering
     * observers (Kitchen, Waiter) with the OrderNotifier.
     * 
     * Part of Facade Pattern: Hides the complexity of setting up observers
     */
    private void setupNotificationSystem() {
        // Create kitchen
        Kitchen mainKitchen = new Kitchen("Main Kitchen");
        notificationController.registerObserver(mainKitchen);

        // Create waiter
        Waiter deliveryWaiter = new Waiter("W1");
        notificationController.registerObserver(deliveryWaiter);

        ui.showNotificationSystemSetup();
    }

    /**
     * Run the interactive order processing workflow
     */
    public void runInteractive() {
        ui.showWelcomeMessage();

        String customerName = ui.readString("Enter customer name");
        String orderType = ui.readOrderType();

        OrderTemplate order = createOrder(orderType);
        order.setCustomerName(customerName);

        ui.displayMenu(menu);
        collectOrderItems(order);

        configureDiscounts();

        PaymentStrategy paymentStrategy = ui.selectPaymentMethod();
        order.setPaymentStrategy(paymentStrategy);

        order.processOrder();
    }

    /**
     * Create an order based on type (Template Method Pattern)
     * 
     * This method uses the Template Method Pattern by creating different
     * order types (DineInOrder, TakeawayOrder, DeliveryOrder) that all
     * extend OrderTemplate.
     */
    private OrderTemplate createOrder(String type) {
        switch (type.toLowerCase()) {
            case "dinein":
                return new DineInOrder(paymentHandler, notificationController, calculator);
            case "takeaway":
                return new TakeawayOrder(paymentHandler, notificationController, calculator);
            case "delivery":
                return new DeliveryOrder(paymentHandler, notificationController, calculator,ui.readString("Enter your addres"));
            default:
                throw new IllegalArgumentException("Invalid order type");
        }
    }

    private void collectOrderItems(OrderTemplate order) {
        while (true) {
            String itemName = ui.readString("Enter menu item name to add (or 'done')");
            if (itemName.equalsIgnoreCase("done")) break;

            MenuItem item = findMenuItemByName(itemName);
            if (item == null) {
                ui.showMessage("Item not found!");
                continue;
            }

            int qty = ui.readInt("Enter quantity");
            item = ui.configureExtras(item);
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
        calculator.addDiscountStrategy(new PizzaDiscount());
        calculator.addDiscountStrategy(new MeatDiscount());
    }
}
