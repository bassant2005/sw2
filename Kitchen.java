// ============================================================================
// Order & Observer Pattern - Improved Version
// ============================================================================

import java.util.*;

/**
 * Kitchen observer - receives order notifications and queues them for preparation
 * Single Responsibility: Handle kitchen order processing
 */
public class Kitchen implements Observer {
    private final String id;
    private final String station;
    private final Queue<OrderTemplate> orderQueue;

    public Kitchen(String id, String station) {
        this.id = id;
        this.station = station;
        this.orderQueue = new LinkedList<>();
    }

    @Override
    public void update(OrderTemplate order) {
        orderQueue.offer(order);
        System.out.println("[Kitchen " + id + "] Received order #" + order.getOrderId() + " -> queued at " + station);
    }

    public OrderTemplate prepareNext() {
        OrderTemplate order = orderQueue.poll();
        if (order != null) {
            System.out.println("[Kitchen " + id + "] Preparing order #" + order.getOrderId());
        }
        return order;
    }

    public boolean hasOrders() {
        return !orderQueue.isEmpty();
    }

    public int getQueueSize() {
        return orderQueue.size();
    }
}

