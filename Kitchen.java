import java.util.*;

/**
 * Kitchen - Concrete Observer in Observer Pattern
 * 
 * This class implements the Observer interface to receive notifications
 * about order events. When an order is placed, the kitchen receives a
 * notification and queues the order for preparation.
 * 
 * Design Pattern: Observer Pattern
 * - Concrete Observer: This class observes order changes through the OrderNotifier
 * - Subject: OrderNotifier notifies this observer when orders are placed
 */
public class Kitchen implements Observer {
    private final String station;
    private final Queue<OrderTemplate> orderQueue;

    public Kitchen(String station1) {
        this.station = station1;
        this.orderQueue = new LinkedList<>();
    }

    /**
     * Update method called by OrderNotifier when an order event occurs
     */
    @Override
    public void update(OrderTemplate order) {
        // Add order to the preparation queue
        orderQueue.offer(order);
        System.out.println("[Kitchen] Received order #" + order.getOrderId() + " -> queued at " + station);
    }
}

