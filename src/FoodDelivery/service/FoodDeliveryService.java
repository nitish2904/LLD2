package FoodDelivery.service;
import FoodDelivery.model.*;
import FoodDelivery.observer.OrderObserver;
import FoodDelivery.strategy.AgentAssignmentStrategy;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
public class FoodDeliveryService {
    private final List<Restaurant> restaurants = new ArrayList<>();
    private final List<DeliveryAgent> agents = new ArrayList<>();
    private final Map<String, Order> orders = new LinkedHashMap<>();
    private final List<OrderObserver> observers = new CopyOnWriteArrayList<>();
    private volatile AgentAssignmentStrategy strategy;
    public FoodDeliveryService(AgentAssignmentStrategy strategy) { this.strategy = strategy; }
    public void addRestaurant(Restaurant r) { restaurants.add(r); }
    public void addAgent(DeliveryAgent a) { agents.add(a); }
    public void addObserver(OrderObserver o) { observers.add(o); }
    public Optional<Order> placeOrder(String customerId, Restaurant restaurant, List<MenuItem> items) {
        Order order = new Order(customerId, restaurant, items);
        observers.forEach(o -> o.onOrderPlaced(order));
        Optional<DeliveryAgent> agentOpt = strategy.assign(agents, restaurant);
        if (agentOpt.isEmpty() || !agentOpt.get().assign()) { System.out.println("[FD] ❌ No agent available"); order.cancel(); return Optional.empty(); }
        order.assignAgent(agentOpt.get()); order.setStatus(OrderStatus.PREPARING);
        orders.put(order.getOrderId(), order);
        System.out.println("[FD] ✅ " + order); return Optional.of(order);
    }
    public void prepareOrder(String id) { Order o = orders.get(id); if (o != null) { o.setStatus(OrderStatus.READY); System.out.println("[FD] 👨‍🍳 Ready: " + o); } }
    public void pickUp(String id) { Order o = orders.get(id); if (o != null) { o.setStatus(OrderStatus.PICKED_UP); System.out.println("[FD] 🏍️ Picked up: " + o); } }
    public void deliver(String id, double rating) {
        Order o = orders.get(id);
        if (o != null) { o.setStatus(OrderStatus.DELIVERED); o.getAgent().release(); o.getAgent().updateRating(rating); observers.forEach(ob -> ob.onOrderDelivered(o)); System.out.println("[FD] 🎉 " + o); }
    }
    public void cancelOrder(String id) { Order o = orders.get(id); if (o != null) { o.cancel(); observers.forEach(ob -> ob.onOrderCancelled(o)); System.out.println("[FD] 🚫 " + o); } }
}
