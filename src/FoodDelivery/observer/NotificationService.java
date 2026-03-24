package FoodDelivery.observer;
import FoodDelivery.model.Order;
public class NotificationService implements OrderObserver {
    public void onOrderPlaced(Order o) { System.out.println("[NOTIF] 📱 Order placed: " + o.getOrderId() + " from " + o.getRestaurant().getName()); }
    public void onOrderDelivered(Order o) { System.out.println("[NOTIF] 📱 Delivered by " + o.getAgent().getName() + " | $" + String.format("%.2f", o.getTotal())); }
    public void onOrderCancelled(Order o) { System.out.println("[NOTIF] 📱 Cancelled: " + o.getOrderId()); }
}
