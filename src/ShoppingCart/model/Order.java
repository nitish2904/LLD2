package ShoppingCart.model;
import java.time.LocalDateTime;
import java.util.*;
public class Order {
    private final String orderId;
    private final String userId;
    private final List<CartItem> items;
    private final double totalAmount;
    private final double discountedAmount;
    private OrderStatus status;
    private final LocalDateTime createdAt;
    public Order(String userId, List<CartItem> items, double discountedAmount) {
        this.orderId = "ORD-" + UUID.randomUUID().toString().substring(0,8).toUpperCase();
        this.userId = userId; this.items = new ArrayList<>(items);
        this.totalAmount = items.stream().mapToDouble(CartItem::getSubtotal).sum();
        this.discountedAmount = discountedAmount; this.status = OrderStatus.CONFIRMED; this.createdAt = LocalDateTime.now();
    }
    public void cancel() { status = OrderStatus.CANCELLED; items.forEach(i -> i.getProduct().restoreStock(i.getQuantity())); }
    public void ship() { status = OrderStatus.SHIPPED; }
    public void deliver() { status = OrderStatus.DELIVERED; }
    public String getOrderId() { return orderId; }
    public String getUserId() { return userId; }
    public List<CartItem> getItems() { return items; }
    public double getTotalAmount() { return totalAmount; }
    public double getDiscountedAmount() { return discountedAmount; }
    public OrderStatus getStatus() { return status; }
    @Override public String toString() {
        return "Order[" + orderId + " | " + userId + " | items=" + items.size() + " | $" + String.format("%.2f", totalAmount) + 
            (discountedAmount < totalAmount ? " → $" + String.format("%.2f", discountedAmount) : "") + " | " + status + "]";
    }
}
