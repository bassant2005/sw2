public class Waiter implements Observer {
    private final String id;
    private final Integer assignedTable;

    public Waiter(String id, Integer assignedTable) {
        this.id = id;
        this.assignedTable = assignedTable;
    }

    @Override
    public void update(OrderTemplate order) {
        System.out.println("[Waiter " + id + "] Notified about order #" + order.getOrderId() 
            + " (status: " + order.getStatus() + ")");
    }

    public void serve(OrderTemplate order) {
        System.out.println("[Waiter " + id + "] Serving order #" + order.getOrderId() 
            + " to table " + assignedTable);
    }

    public String getId() {
        return id;
    }

    public Integer getAssignedTable() {
        return assignedTable;
    }
}

