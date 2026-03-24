package OnlineBookstore.model;
import java.util.*; 
public class Order {
    public enum Status { PLACED, SHIPPED, DELIVERED, CANCELLED }
    private final String orderId, userId; private final List<CartItem> items; private final double total; private Status status;
    public Order(String userId, List<CartItem> items, double total) {
        this.orderId = "ORD-" + UUID.randomUUID().toString().substring(0,6).toUpperCase();
        this.userId = userId; this.items = new ArrayList<>(items); this.total = total; this.status = Status.PLACED;
    }
    public void setStatus(Status s) { this.status = s; }
    public String getOrderId() { return orderId; }
    public String getUserId() { return userId; }
    public List<CartItem> getItems() { return items; }
    public double getTotal() { return total; }
    public Status getStatus() { return status; }
    @Override public String toString() { return "Order[" + orderId + " | " + userId + " | " + items.size() + " items | $" + String.format("%.2f", total) + " | " + status + "]"; }
}
