import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class RestaurantApp {

    // ---------------------------
    // Domain: Menu Items & Decorator
    // ---------------------------

    public interface MenuItem {
        String getDescription();
        double getPrice();
    }

    public static class PizzaItem implements MenuItem {
        private final String name;
        private final double basePrice;

        public PizzaItem(String name, double basePrice) {
            this.name = name;
            this.basePrice = basePrice;
        }

        @Override
        public String getDescription() {
            return name + " (Pizza)";
        }

        @Override
        public double getPrice() {
            return basePrice;
        }
    }

    public static class BurgerItem implements MenuItem {
        private final String name;
        private final double basePrice;

        public BurgerItem(String name, double basePrice) {
            this.name = name;
            this.basePrice = basePrice;
        }

        @Override
        public String getDescription() {
            return name + " (Burger)";
        }

        @Override
        public double getPrice() {
            return basePrice;
        }
    }

    public static abstract class MenuItemDecorator implements MenuItem {
        protected final MenuItem wrapped;

        public MenuItemDecorator(MenuItem wrapped) {
            this.wrapped = wrapped;
        }

        @Override
        public String getDescription() {
            return wrapped.getDescription();
        }

        @Override
        public double getPrice() {
            return wrapped.getPrice();
        }
    }

    public static class ExtraCheeseDecorator extends MenuItemDecorator {
        private final double extraPrice;

        public ExtraCheeseDecorator(MenuItem wrapped, double extraPrice) {
            super(wrapped);
            this.extraPrice = extraPrice;
        }

        @Override
        public String getDescription() {
            return wrapped.getDescription() + " + Extra Cheese";
        }

        @Override
        public double getPrice() {
            return wrapped.getPrice() + extraPrice;
        }
    }

    public static class SauceDecorator extends MenuItemDecorator {
        private final String sauceType;
        private final double extraPrice;

        public SauceDecorator(MenuItem wrapped, String sauceType, double extraPrice) {
            super(wrapped);
            this.sauceType = sauceType;
            this.extraPrice = extraPrice;
        }

        @Override
        public String getDescription() {
            return wrapped.getDescription() + " + " + sauceType + " sauce";
        }

        @Override
        public double getPrice() {
            return wrapped.getPrice() + extraPrice;
        }
    }

    // ---------------------------
    // Menu & Abstract Factory
    // ---------------------------

    public static class Menu {
        private final List<MenuItem> items = new ArrayList<>();

        public void addItem(MenuItem item) {
            items.add(item);
        }

        public List<MenuItem> getItems() {
            return Collections.unmodifiableList(items);
        }

        public MenuItem findItemByName(String namePart) {
            for (MenuItem it : items) {
                if (it.getDescription().toLowerCase().contains(namePart.toLowerCase()))
                    return it;
            }
            return null;
        }
    }

    public static abstract class MenuFactory {
        public abstract Menu createMenu();
    }

    public static class VegMenuFactory extends MenuFactory {
        @Override
        public Menu createMenu() {
            Menu m = new Menu();
            m.addItem(new PizzaItem("Italian Veg", 60.0));
            m.addItem(new PizzaItem("Margherita", 55.0));
            m.addItem(new BurgerItem("Veggie Classic", 45.0));
            return m;
        }
    }

    public static class NonVegMenuFactory extends MenuFactory {
        @Override
        public Menu createMenu() {
            Menu m = new Menu();
            m.addItem(new PizzaItem("Italian Chicken", 80.0));
            m.addItem(new PizzaItem("Eastern Meat Feast", 95.0));
            m.addItem(new BurgerItem("Classic Beef", 70.0));
            return m;
        }
    }

    public static class KidsMenuFactory extends MenuFactory {
        @Override
        public Menu createMenu() {
            Menu m = new Menu();
            m.addItem(new PizzaItem("Kids Small Margherita", 30.0));
            m.addItem(new BurgerItem("Kids Mini Burger", 25.0));
            return m;
        }
    }

    // ---------------------------
    // Order, OrderItem
    // ---------------------------

    public static class OrderItem {
        private final MenuItem menuItem;
        private final int quantity;

        public OrderItem(MenuItem menuItem, int quantity) {
            this.menuItem = menuItem;
            this.quantity = Math.max(1, quantity);
        }

        public MenuItem getMenuItem() {
            return menuItem;
        }

        public int getQuantity() {
            return quantity;
        }

        public double getSubtotal() {
            return menuItem.getPrice() * quantity;
        }

        public String getDescription() {
            return menuItem.getDescription() + " x" + quantity;
        }
    }

    public enum OrderStatus { NEW, PLACED, PREPARING, READY, SERVED, COMPLETED, CANCELLED }

    // ---------------------------
    // Observer
    // ---------------------------

    public interface Observer {
        void update(OrderTemplate order);
    }

    public static class Kitchen implements Observer {
        private final String id;
        private final String station;
        private final Queue<OrderTemplate> queue = new LinkedList<>();

        public Kitchen(String id, String station) {
            this.id = id;
            this.station = station;
        }

        @Override
        public void update(OrderTemplate order) {
            System.out.println("[Kitchen " + id + "] Received order #" + order.getOrderId() + " -> queued at " + station);
            queue.offer(order);
        }

        public void prepareNext() {
            OrderTemplate o = queue.poll();
            if (o != null) {
                System.out.println("[Kitchen " + id + "] Preparing order #" + o.getOrderId());
            }
        }
    }

    public static class Waiter implements Observer {
        private final String id;
        private final Integer tableNumber;

        public Waiter(String id, Integer tableNumber) {
            this.id = id;
            this.tableNumber = tableNumber;
        }

        @Override
        public void update(OrderTemplate order) {
            System.out.println("[Waiter " + id + "] Notified about order #" + order.getOrderId() + " (status: " + order.getStatus() + ")");
        }

        public void serve(OrderTemplate o) {
            System.out.println("[Waiter " + id + "] Serving order #" + o.getOrderId() + " to table " + tableNumber);
        }
    }

    public static class OrderNotifier {
        private final List<Observer> observers = new ArrayList<>();

        public void registerObserver(Observer o) {
            observers.add(o);
        }

        public void removeObserver(Observer o) {
            observers.remove(o);
        }

        public void notifyObservers(OrderTemplate order) {
            for (Observer o : new ArrayList<>(observers)) {
                o.update(order);
            }
        }
    }

    // ---------------------------
    // Payment Strategy
    // ---------------------------

    public interface PaymentStrategy {
        boolean pay(double amount);
    }

    public static class CashPayment implements PaymentStrategy {
        private final String cashierId;

        public CashPayment(String cashierId) {
            this.cashierId = cashierId;
        }

        @Override
        public boolean pay(double amount) {
            System.out.println("[CashPayment] Received cash payment of " + format(amount) + " by cashier " + cashierId);
            return true;
        }
    }

    public static class CreditCardPayment implements PaymentStrategy {
        private final String cardNumberMasked;
        private final String authorizationCode;

        public CreditCardPayment(String cardNumberMasked, String authorizationCode) {
            this.cardNumberMasked = cardNumberMasked;
            this.authorizationCode = authorizationCode;
        }

        @Override
        public boolean pay(double amount) {
            System.out.println("[CreditCardPayment] Simulating authorization for " + format(amount) +
                    " using card " + cardNumberMasked + " auth=" + authorizationCode);
            return Math.random() > 0.05;
        }
    }

    public static class MobileWalletPayment implements PaymentStrategy {
        private final String walletId;

        public MobileWalletPayment(String walletId) {
            this.walletId = walletId;
        }

        @Override
        public boolean pay(double amount) {
            System.out.println("[MobileWalletPayment] Calling wallet gateway for " + format(amount) + " wallet=" + walletId);
            return Math.random() > 0.1;
        }
    }

    public static class PaymentHandler {
        public boolean processPayment(double amount, PaymentStrategy strategy) {
            return strategy.pay(amount);
        }
    }

    // ---------------------------
    // Discount Strategy
    // ---------------------------

    public interface DiscountStrategy {
        double apply(List<OrderItem> items);
    }

    public static class PizzaDiscount implements DiscountStrategy {
        private final double percent;

        public PizzaDiscount(double percent) {
            this.percent = percent;
        }

        @Override
        public double apply(List<OrderItem> items) {
            double pizzaSubtotal = 0.0;
            for (OrderItem it : items) {
                if (it.getMenuItem().getDescription().toLowerCase().contains("pizza")) {
                    pizzaSubtotal += it.getSubtotal();
                }
            }
            return pizzaSubtotal * percent / 100.0;
        }
    }

    public static class MeatDiscount implements DiscountStrategy {
        private final double fixedAmount;

        public MeatDiscount(double fixedAmount) {
            this.fixedAmount = fixedAmount;
        }

        @Override
        public double apply(List<OrderItem> items) {
            double meatSubtotal = 0.0;
            for (OrderItem it : items) {
                String desc = it.getMenuItem().getDescription().toLowerCase();
                if (desc.contains("meat") || desc.contains("beef") || desc.contains("chicken")) {
                    meatSubtotal += it.getSubtotal();
                }
            }
            return Math.min(fixedAmount, meatSubtotal);
        }
    }

    // ---------------------------
    // Order Calculator
    // ---------------------------

    public static class OrderCalculator {
        private final double taxPercent;
        private final List<DiscountStrategy> discountStrategies = new ArrayList<>();

        public OrderCalculator(double taxPercent) {
            this.taxPercent = taxPercent;
        }

        public void addDiscountStrategy(DiscountStrategy d) {
            discountStrategies.add(d);
        }

        public double calculateSubtotal(List<OrderItem> items) {
            double s = 0.0;
            for (OrderItem it : items) s += it.getSubtotal();
            return s;
        }

        public double calculateDiscount(List<OrderItem> items) {
            double totalDiscount = 0.0;
            for (DiscountStrategy ds : discountStrategies) totalDiscount += ds.apply(items);
            return totalDiscount;
        }

        public double calculateTax(double amountAfterDiscount) {
            return amountAfterDiscount * taxPercent / 100.0;
        }

        public double calculateTotal(List<OrderItem> items) {
            double subtotal = calculateSubtotal(items);
            double discount = calculateDiscount(items);
            double afterDiscount = Math.max(0.0, subtotal - discount);
            double tax = calculateTax(afterDiscount);
            return afterDiscount + tax;
        }
    }

    // ---------------------------
    // Billing (Singleton)
    // ---------------------------

    public static class Bill {
        private final int orderId;
        private final List<OrderItem> items;
        private final double subtotal;
        private final double discount;
        private final double tax;
        private final double total;

        public Bill(int orderId, List<OrderItem> items, double subtotal, double discount, double tax, double total) {
            this.orderId = orderId;
            this.items = new ArrayList<>(items);
            this.subtotal = subtotal;
            this.discount = discount;
            this.tax = tax;
            this.total = total;
        }

        public int getOrderId() { return orderId; }
        public List<OrderItem> getItems() { return Collections.unmodifiableList(items); }
        public double getSubtotal() { return subtotal; }
        public double getDiscount() { return discount; }
        public double getTax() { return tax; }
        public double getTotal() { return total; }
    }

    public interface BillPrinter { void print(Bill bill); }

    public static class ConsoleBillPrinter implements BillPrinter {
        @Override
        public void print(Bill bill) {
            System.out.println("\n--- BILL (Order #" + bill.getOrderId() + ") ---");
            for (OrderItem it : bill.getItems()) {
                System.out.printf("%-40s %8.2f\n", it.getDescription(), it.getSubtotal());
            }
            System.out.printf("%-40s %8.2f\n", "SUBTOTAL", bill.getSubtotal());
            System.out.printf("%-40s %8.2f\n", "DISCOUNT", -bill.getDiscount());
            System.out.printf("%-40s %8.2f\n", "TAX", bill.getTax());
            System.out.printf("%-40s %8.2f\n", "TOTAL", bill.getTotal());
            System.out.println("------------------------------\n");
        }
    }

    public static class BillGenerator {
        public Bill generateBill(int orderId, List<OrderItem> items, OrderCalculator calc) {
            double subtotal = calc.calculateSubtotal(items);
            double discount = calc.calculateDiscount(items);
            double afterDiscount = Math.max(0.0, subtotal - discount);
            double tax = calc.calculateTax(afterDiscount);
            double total = afterDiscount + tax;
            return new Bill(orderId, items, subtotal, discount, tax, total);
        }
    }

    public static class BillingSystem {
        private static BillingSystem instance;
        private final BillGenerator generator;
        private BillPrinter printer;

        private BillingSystem() {
            this.generator = new BillGenerator();
            this.printer = new ConsoleBillPrinter();
        }

        public static BillingSystem getInstance() {
            if (instance == null) instance = new BillingSystem();
            return instance;
        }

        public void setPrinter(BillPrinter printer) { this.printer = printer; }

        public Bill generateBill(int orderId, List<OrderItem> items, OrderCalculator calc) {
            return generator.generateBill(orderId, items, calc);
        }

        public void generateAndPrintBill(int orderId, List<OrderItem> items, OrderCalculator calc) {
            Bill b = generateBill(orderId, items, calc);
            if (printer != null) printer.print(b);
        }
    }

    // ---------------------------
    // Order Template + concrete orders
    // ---------------------------

    public static abstract class OrderTemplate {
        private static final AtomicInteger ID_GEN = new AtomicInteger(1000);

        private final int orderId;
        protected final List<OrderItem> items = new ArrayList<>();
        protected final PaymentHandler paymentHandler;
        protected final OrderNotifier notifier;
        protected final OrderCalculator calculator;
        protected OrderStatus status = OrderStatus.NEW;
        protected PaymentStrategy paymentStrategy;
        protected String customerName = "Guest";
        protected String tableNumber = null;

        public OrderTemplate(PaymentHandler paymentHandler, OrderNotifier notifier, OrderCalculator calculator) {
            this.orderId = ID_GEN.getAndIncrement();
            this.paymentHandler = paymentHandler;
            this.notifier = notifier;
            this.calculator = calculator;
        }

        public int getOrderId() { return orderId; }
        public OrderStatus getStatus() { return status; }
        public void addItem(OrderItem it) { items.add(it); }
        public List<OrderItem> getItems() { return Collections.unmodifiableList(items); }
        public void setPaymentStrategy(PaymentStrategy s) { this.paymentStrategy = s; }
        public void setCustomerName(String name) { this.customerName = name; }
        public void setTableNumber(String t) { this.tableNumber = t; }

        public final void processOrder() {
            status = OrderStatus.PLACED;
            System.out.println("\n[Order #" + orderId + "] Placed by " + customerName + " (status=" + status + ")");
            notifySystems();
            calculateTotal();
            if (!handlePayment()) {
                status = OrderStatus.CANCELLED;
                System.out.println("[Order #" + orderId + "] Payment failed -> CANCELLED");
                return;
            }
            printBill();
            status = OrderStatus.COMPLETED;
            System.out.println("[Order #" + orderId + "] Completed (status=" + status + ")");
        }

        protected abstract void calculateTotal();
        protected abstract boolean handlePayment();
        protected abstract void notifySystems();
        protected abstract void printBill();
    }

    public static class DineInOrder extends OrderTemplate {
        public DineInOrder(PaymentHandler paymentHandler, OrderNotifier notifier, OrderCalculator calculator, String tableNumber) {
            super(paymentHandler, notifier, calculator);
            setTableNumber(tableNumber);
        }

        @Override
        protected void calculateTotal() {
            double subtotal = calculator.calculateSubtotal(items);
            double discount = calculator.calculateDiscount(items);
            double afterDiscount = Math.max(0.0, subtotal - discount);
            double tax = calculator.calculateTax(afterDiscount);
            double total = afterDiscount + tax;
            System.out.printf("[DineInOrder #%d] Subtotal=%.2f Discount=%.2f Tax=%.2f => Total=%.2f\n",
                    getOrderId(), subtotal, discount, tax, total);
        }

        @Override
        protected boolean handlePayment() {
            if (paymentStrategy == null) return true;
            return paymentHandler.processPayment(calculator.calculateTotal(items), paymentStrategy);
        }

        @Override
        protected void notifySystems() { notifier.notifyObservers(this); }

        @Override
        protected void printBill() { BillingSystem.getInstance().generateAndPrintBill(getOrderId(), items, calculator); }
    }

    public static class DeliveryOrder extends OrderTemplate {
        private final String deliveryAddress;
        private final double deliveryFee;

        public DeliveryOrder(PaymentHandler paymentHandler, OrderNotifier notifier, OrderCalculator calculator,
                             String deliveryAddress, double deliveryFee) {
            super(paymentHandler, notifier, calculator);
            this.deliveryAddress = deliveryAddress;
            this.deliveryFee = deliveryFee;
        }

        @Override
        protected void calculateTotal() {
            double subtotal = calculator.calculateSubtotal(items);
            double discount = calculator.calculateDiscount(items);
            double afterDiscount = Math.max(0.0, subtotal - discount);
            double tax = calculator.calculateTax(afterDiscount + deliveryFee);
            double total = afterDiscount + tax + deliveryFee;
            System.out.printf("[DeliveryOrder #%d] Subtotal=%.2f Discount=%.2f DeliveryFee=%.2f Tax=%.2f => Total=%.2f\n",
                    getOrderId(), subtotal, discount, deliveryFee, tax, total);
        }

        @Override
        protected boolean handlePayment() {
            if (paymentStrategy == null) return true;
            return paymentHandler.processPayment(calculator.calculateTotal(items) + deliveryFee, paymentStrategy);
        }

        @Override
        protected void notifySystems() { notifier.notifyObservers(this); }

        @Override
        protected void printBill() { BillingSystem.getInstance().generateAndPrintBill(getOrderId(), items, calculator); }
    }

    public static class TakeawayOrder extends OrderTemplate {
        private final String pickupTime;

        public TakeawayOrder(PaymentHandler paymentHandler, OrderNotifier notifier, OrderCalculator calculator, String pickupTime) {
            super(paymentHandler, notifier, calculator);
            this.pickupTime = pickupTime;
        }

        @Override
        protected void calculateTotal() {
            double subtotal = calculator.calculateSubtotal(items);
            double discount = calculator.calculateDiscount(items);
            double afterDiscount = Math.max(0.0, subtotal - discount);
            double tax = calculator.calculateTax(afterDiscount);
            double total = afterDiscount + tax;
            System.out.printf("[TakeawayOrder #%d] Subtotal=%.2f Discount=%.2f Tax=%.2f => Total=%.2f (Pickup=%s)\n",
                    getOrderId(), subtotal, discount, tax, total, pickupTime);
        }

        @Override
        protected boolean handlePayment() {
            if (paymentStrategy == null) return true;
            return paymentHandler.processPayment(calculator.calculateTotal(items), paymentStrategy);
        }

        @Override
        protected void notifySystems() { notifier.notifyObservers(this); }

        @Override
        protected void printBill() { BillingSystem.getInstance().generateAndPrintBill(getOrderId(), items, calculator); }
    }

    // ---------------------------
    // UI Helper
    // ---------------------------

    public static class UIController {
        private final Scanner sc = new Scanner(System.in);

        public int readInt(String prompt) {
            while (true) {
                System.out.print(prompt + ": ");
                try { return Integer.parseInt(sc.nextLine()); }
                catch (Exception e) { System.out.println("Invalid number!"); }
            }
        }

        public String readString(String prompt) {
            System.out.print(prompt + ": ");
            return sc.nextLine();
        }

        public void displayMenu(Menu menu) {
            int idx = 1;
            for (MenuItem it : menu.getItems()) {
                System.out.printf("%d) %s -> %.2f\n", idx++, it.getDescription(), it.getPrice());
            }
        }
    }

    // ---------------------------
    // Main Controller (Interactive)
    // ---------------------------

    public static class MainController {
        private final Menu menu;
        private final PaymentHandler paymentHandler = new PaymentHandler();
        private final OrderNotifier notificationController = new OrderNotifier();
        private final OrderCalculator calculator = new OrderCalculator(14.0); // 14% tax
        private final UIController ui = new UIController();

        public MainController(Menu menu) { this.menu = menu; }

        public void runInteractive() {
            System.out.println("Welcome to the Restaurant Ordering System!");

            String customerName = ui.readString("Enter customer name");
            String orderType = ui.readString("Enter order type (dinein/delivery/takeaway)").toLowerCase();

            Map<String, Object> params = new HashMap<>();
            if (orderType.equals("dinein")) {
                String table = ui.readString("Enter table number");
                params.put("table", table);
            } else if (orderType.equals("delivery")) {
                String addr = ui.readString("Enter delivery address");
                params.put("addr", addr);
                double fee = 10 + Math.random() * 90;
                System.out.println("Delivery fee (randomly generated): " + RestaurantApp.format(fee));
                params.put("fee", fee);
            } else if (orderType.equals("takeaway")) {
                String pickup = ui.readString("Enter pickup time (HH:MM)");
                params.put("pickup", pickup);
            }

            OrderTemplate order = createOrder(orderType, paymentHandler, notificationController, calculator, params);
            order.setCustomerName(customerName);

            ui.displayMenu(menu);

            while (true) {
                String itemName = ui.readString("Enter menu item name to add (or 'done')");
                if (itemName.equalsIgnoreCase("done")) break;

                MenuItem item = menu.findItemByName(itemName);
                if (item == null) { System.out.println("Item not found!"); continue; }

                int qty = ui.readInt("Enter quantity");

                String cheese = ui.readString("Add extra cheese? (yes/no)");
                if (cheese.equalsIgnoreCase("yes")) {
                    double cheesePrice = 25; // fixed price
                    item = new ExtraCheeseDecorator(item, cheesePrice);
                }

                String sauce = ui.readString("Add sauce? (none for no sauce)");
                if (!sauce.equalsIgnoreCase("none")) {
                    double saucePrice = 20; // fixed price
                    item = new SauceDecorator(item, sauce, saucePrice);
                }

                order.addItem(new OrderItem(item, qty));
            }

            calculator.addDiscountStrategy(new PizzaDiscount(10.0));
            calculator.addDiscountStrategy(new MeatDiscount(5.0));

            System.out.println("\nSelect payment method:\n1) Cash  2) Credit Card  3) Mobile Wallet");
            int payOption = ui.readInt("Enter choice");
            PaymentStrategy strategy;
            switch (payOption) {
                case 1: strategy = new CashPayment(ui.readString("Enter cashier ID")); break;
                case 2: strategy = new CreditCardPayment(ui.readString("Enter card number"), ui.readString("Enter auth code")); break;
                case 3: strategy = new MobileWalletPayment(ui.readString("Enter wallet ID")); break;
                default:
                    System.out.println("Invalid, defaulting to cash.");
                    strategy = new CashPayment("Default");
            }
            order.setPaymentStrategy(strategy);

            notificationController.registerObserver(new Kitchen("K1", "Main"));
            notificationController.registerObserver(new Waiter("W1", 5));

            order.processOrder();
        }

        public OrderTemplate createOrder(String type, PaymentHandler handler, OrderNotifier notifier, OrderCalculator calc, Map<String,Object> params) {
            switch (type.toLowerCase()) {
                case "dinein":
                    return new DineInOrder(handler, notifier, calc, (String)params.get("table"));
                case "delivery":
                    return new DeliveryOrder(handler, notifier, calc, (String)params.get("addr"), (double)params.get("fee"));
                case "takeaway":
                    return new TakeawayOrder(handler, notifier, calc, (String)params.get("pickup"));
                default:
                    throw new IllegalArgumentException("Invalid order type");
            }
        }
    }

    // ---------------------------
    // Main
    // ---------------------------

    public static void main(String[] args) {
        MenuFactory factory = new NonVegMenuFactory(); // can choose any
        Menu menu = factory.createMenu();
        MainController controller = new MainController(menu);
        controller.runInteractive();
    }

    // ---------------------------
    // Utils
    // ---------------------------
    public static String format(double val) { return String.format("%.2f", val); }

}