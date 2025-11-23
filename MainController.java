public class MainController {
    private final Menu menu;
    private final PaymentHandler paymentHandler = new PaymentHandler();
    private final OrderNotifier notificationController = new OrderNotifier();
    private final OrderCalculator calculator = new OrderCalculator(14.0); // 14% tax
    private final UIController ui;

    public MainController(Menu menu, UIController ui) {
        if (menu == null) throw new IllegalArgumentException("Menu cannot be null");
        this.menu = menu;
        this.ui = ui;
        setupNotificationSystem();
    }

    private void setupNotificationSystem() {
        // Create kitchens
        Kitchen mainKitchen = new Kitchen("Main Kitchen");
        notificationController.registerObserver(mainKitchen);

        Waiter deliveryWaiter = new Waiter("W1");
        notificationController.registerObserver(deliveryWaiter);

        ui.showNotificationSystemSetup();
    }

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
