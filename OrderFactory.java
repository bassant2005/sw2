import java.util.*;

public class OrderFactory {
    public static OrderTemplate createOrder(String orderType, PaymentHandler paymentHandler, OrderNotifier notifier, OrderCalculator calculator, Map<String, Object> parameters) {
        if (orderType == null || orderType.trim().isEmpty()) {
            throw new IllegalArgumentException("Order type cannot be null or empty");
        }

        String type = orderType.toLowerCase().trim();

        switch (type) {
            case "dinein":
                String tableNumber = (String) parameters.get("table");
                if (tableNumber == null || tableNumber.trim().isEmpty()) {
                    throw new IllegalArgumentException("Table number is required for dine-in orders");
                }
                return new DineInOrder(paymentHandler, notifier, calculator);

            case "takeaway":
                String pickupTime = (String) parameters.get("pickup");
                if (pickupTime == null || pickupTime.trim().isEmpty()) {
                    throw new IllegalArgumentException("Pickup time is required for takeaway orders");
                }
                return new TakeawayOrder(paymentHandler, notifier, calculator);

            default:
                throw new IllegalArgumentException("Invalid order type: " + orderType);
        }
    }
}

