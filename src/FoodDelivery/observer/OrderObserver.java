package FoodDelivery.observer;
import FoodDelivery.model.Order;
public interface OrderObserver {
    void onOrderPlaced(Order o);
    void onOrderDelivered(Order o);
    void onOrderCancelled(Order o);
}
