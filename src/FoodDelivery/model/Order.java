package FoodDelivery.model;
import java.util.*;
public class Order {
    private final String orderId, customerId;
    private final Restaurant restaurant;
    private final List<MenuItem> items;
    private DeliveryAgent agent;
    private OrderStatus status;
    private final double total;
    public Order(String customerId, Restaurant restaurant, List<MenuItem> items) {
        this.orderId = "FD-" + UUID.randomUUID().toString().substring(0,6).toUpperCase();
        this.customerId = customerId; this.restaurant = restaurant; this.items = new ArrayList<>(items);
        this.total = items.stream().mapToDouble(MenuItem::getPrice).sum(); this.status = OrderStatus.PLACED;
    }
    public void assignAgent(DeliveryAgent a) { this.agent = a; }
    public void setStatus(OrderStatus s) { this.status = s; }
    public void cancel() { status = OrderStatus.CANCELLED; if (agent != null) agent.release(); }
    public String getOrderId() { return orderId; }
    public String getCustomerId() { return customerId; }
    public Restaurant getRestaurant() { return restaurant; }
    public DeliveryAgent getAgent() { return agent; }
    public OrderStatus getStatus() { return status; }
    public double getTotal() { return total; }
    @Override public String toString() { return "Order[" + orderId + " | " + customerId + " | " + restaurant.getName() + " | $" + String.format("%.2f", total) + " | " + status + " | agent=" + (agent != null ? agent.getName() : "none") + "]"; }
}
