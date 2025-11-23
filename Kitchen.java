// ============================================================================
// Order & Observer Pattern - Improved Version
// ============================================================================

import java.util.*;

/**
 * Kitchen observer - receives order notifications and queues them for preparation
 * Single Responsibility: Handle kitchen order processing
 */
public class Kitchen implements Observer {
    private final String station;
    private final Queue<OrderTemplate> orderQueue;

    public Kitchen(String station1) {
        this.station = station1;
        this.orderQueue = new LinkedList<>();
    }

    @Override
    public void update(OrderTemplate order) {
        orderQueue.offer(order);
        System.out.println("[Kitchen] Received order #" + order.getOrderId() + " -> queued at " + station);
    }
}

