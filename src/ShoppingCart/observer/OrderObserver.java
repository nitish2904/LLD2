package ShoppingCart.observer;
import ShoppingCart.model.Order;
public interface OrderObserver {
    void onOrderPlaced(Order order);
    void onOrderCancelled(Order order);
    void onOrderShipped(Order order);
}
