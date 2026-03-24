package OnlineBookstore.observer;
import OnlineBookstore.model.Order;
public class EmailNotifier implements OrderObserver {
    public void onOrderPlaced(Order o) { System.out.println("[EMAIL] 📧 Order placed: " + o.getOrderId() + " for " + o.getUserId() + " — $" + String.format("%.2f", o.getTotal())); }
    public void onOrderShipped(Order o) { System.out.println("[EMAIL] 📧 Shipped: " + o.getOrderId()); }
}
