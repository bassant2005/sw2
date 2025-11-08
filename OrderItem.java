class OrderItem {
    private final MenuItem item;
    private final int quantity;

    public OrderItem(MenuItem item, int quantity) {
        this.item = item;
        this.quantity = Math.max(1, quantity);
    }

    public MenuItem getMenuItem() { return item; }
    public int getQuantity() { return quantity; }
    public double getSubtotal() { return item.getPrice() * quantity; }
    public String getDescription() { return item.getDescription() + " x" + quantity; }
}