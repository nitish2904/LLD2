package ShoppingCart.observer;
import ShoppingCart.model.Order;
public class EmailNotifier implements OrderObserver {
    public void onOrderPlaced(Order order) { System.out.println("[EMAIL] 📧 Order confirmed: " + order.getOrderId() + " | $" + String.format("%.2f", order.getDiscountedAmount())); }
    public void onOrderCancelled(Order order) { System.out.println("[EMAIL] 📧 Order cancelled: " + order.getOrderId()); }
    public void onOrderShipped(Order order) { System.out.println("[EMAIL] 📧 Order shipped: " + order.getOrderId()); }
}
