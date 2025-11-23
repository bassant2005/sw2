public class Waiter implements Observer {
    private final String id;

    public Waiter(String id) {
        this.id = id;
    }

    @Override
    public void update(OrderTemplate order) {
        System.out.println("[Waiter " + id + "] Notified about order #" + order.getOrderId() 
            + " (status: " + order.getStatus() + ")");
    }
}

