package ShoppingCart.observer;
import ShoppingCart.model.Order;
import ShoppingCart.model.CartItem;
public class InventoryTracker implements OrderObserver {
    public void onOrderPlaced(Order order) { order.getItems().forEach(i -> System.out.println("[INVENTORY] 📦 Reserved: " + i.getProduct().getName() + " x" + i.getQuantity())); }
    public void onOrderCancelled(Order order) { System.out.println("[INVENTORY] 🔄 Stock restored for " + order.getOrderId()); }
    public void onOrderShipped(Order order) { System.out.println("[INVENTORY] 🚚 Shipped: " + order.getOrderId()); }
}
