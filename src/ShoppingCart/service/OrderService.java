package ShoppingCart.service;
import ShoppingCart.model.*;
import ShoppingCart.observer.OrderObserver;
import ShoppingCart.strategy.DiscountStrategy;
import ShoppingCart.strategy.NoDiscount;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
public class OrderService {
    private volatile DiscountStrategy discountStrategy;
    private final List<OrderObserver> observers = new CopyOnWriteArrayList<>();
    private final Map<String, Order> orders = new LinkedHashMap<>();
    public OrderService() { this.discountStrategy = new NoDiscount(); }
    public void setDiscountStrategy(DiscountStrategy strategy) { this.discountStrategy = strategy; System.out.println("[ORDER] Discount: " + strategy.getDescription()); }
    public void addObserver(OrderObserver observer) { observers.add(observer); }
    public Optional<Order> checkout(Cart cart) {
        if (cart.isEmpty()) { System.out.println("[ORDER] ❌ Cart is empty"); return Optional.empty(); }
        // Reserve stock
        List<CartItem> reserved = new ArrayList<>();
        for (CartItem item : cart.getItems()) {
            if (!item.getProduct().reserveStock(item.getQuantity())) {
                reserved.forEach(r -> r.getProduct().restoreStock(r.getQuantity()));
                System.out.println("[ORDER] ❌ Insufficient stock: " + item.getProduct().getName());
                return Optional.empty();
            }
            reserved.add(item);
        }
        double discounted = discountStrategy.applyDiscount(cart);
        Order order = new Order(cart.getUserId(), new ArrayList<>(cart.getItems()), discounted);
        orders.put(order.getOrderId(), order);
        cart.clear();
        observers.forEach(o -> o.onOrderPlaced(order));
        System.out.println("[ORDER] ✅ " + order);
        return Optional.of(order);
    }
    public boolean cancelOrder(String orderId) {
        Order order = orders.get(orderId);
        if (order == null || order.getStatus() == OrderStatus.CANCELLED) return false;
        order.cancel(); observers.forEach(o -> o.onOrderCancelled(order));
        System.out.println("[ORDER] 🚫 Cancelled: " + order); return true;
    }
    public void shipOrder(String orderId) {
        Order order = orders.get(orderId);
        if (order != null && order.getStatus() == OrderStatus.CONFIRMED) {
            order.ship(); observers.forEach(o -> o.onOrderShipped(order));
            System.out.println("[ORDER] 🚚 Shipped: " + order);
        }
    }
}
