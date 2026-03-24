package FoodDelivery;
import FoodDelivery.model.*;
import FoodDelivery.observer.NotificationService;
import FoodDelivery.service.FoodDeliveryService;
import FoodDelivery.strategy.NearestAgentStrategy;
import java.util.*;
public class FoodDeliveryMain {
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║   🍕 FOOD DELIVERY SYSTEM DEMO    ║");
        System.out.println("╚════════════════════════════════════╝\n");
        FoodDeliveryService service = new FoodDeliveryService(new NearestAgentStrategy());
        service.addObserver(new NotificationService());
        Restaurant pizzaHut = new Restaurant("R1", "Pizza Hut", 1, 1);
        pizzaHut.addItem(new MenuItem("Margherita", 12.99));
        pizzaHut.addItem(new MenuItem("Pepperoni", 14.99));
        Restaurant sushi = new Restaurant("R2", "Sushi Bar", 5, 5);
        sushi.addItem(new MenuItem("Salmon Roll", 18.99));
        service.addRestaurant(pizzaHut); service.addRestaurant(sushi);
        service.addAgent(new DeliveryAgent("A1", "Raj", 0, 0));
        service.addAgent(new DeliveryAgent("A2", "Priya", 4, 4));

        System.out.println("═══ SCENARIO 1: Full delivery lifecycle ═══\n");
        var o1 = service.placeOrder("C1", pizzaHut, List.of(pizzaHut.getMenu().get(0), pizzaHut.getMenu().get(1)));
        if (o1.isPresent()) { String id = o1.get().getOrderId(); service.prepareOrder(id); service.pickUp(id); service.deliver(id, 4.8); }

        System.out.println("\n═══ SCENARIO 2: Cancel order ═══\n");
        var o2 = service.placeOrder("C2", sushi, List.of(sushi.getMenu().get(0)));
        if (o2.isPresent()) service.cancelOrder(o2.get().getOrderId());

        System.out.println("\n═══ SCENARIO 3: All agents busy ═══\n");
        service.placeOrder("C3", pizzaHut, List.of(pizzaHut.getMenu().get(0)));
        service.placeOrder("C4", sushi, List.of(sushi.getMenu().get(0)));
        service.placeOrder("C5", pizzaHut, List.of(pizzaHut.getMenu().get(1)));

        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║   ✅ ALL SCENARIOS COMPLETE         ║");
        System.out.println("╚════════════════════════════════════╝");
    }
}
