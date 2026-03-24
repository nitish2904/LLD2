package OnlineBookstore.observer;
import OnlineBookstore.model.Order;
public interface OrderObserver { void onOrderPlaced(Order o); void onOrderShipped(Order o); }
