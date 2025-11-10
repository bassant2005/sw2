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
                return new DineInOrder(paymentHandler, notifier, calculator, tableNumber);

            case "delivery":
                String address = (String) parameters.get("addr");
                Double fee = (Double) parameters.get("fee");
                if (address == null || address.trim().isEmpty()) {
                    throw new IllegalArgumentException("Delivery address is required");
                }
                if (fee == null || fee < 0) {
                    throw new IllegalArgumentException("Delivery fee must be non-negative");
                }
                return new DeliveryOrder(paymentHandler, notifier, calculator, address, fee);

            case "takeaway":
                String pickupTime = (String) parameters.get("pickup");
                if (pickupTime == null || pickupTime.trim().isEmpty()) {
                    throw new IllegalArgumentException("Pickup time is required for takeaway orders");
                }
                return new TakeawayOrder(paymentHandler, notifier, calculator, pickupTime);

            default:
                throw new IllegalArgumentException("Invalid order type: " + orderType);
        }
    }
}

