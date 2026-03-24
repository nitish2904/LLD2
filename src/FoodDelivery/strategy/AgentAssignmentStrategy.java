package FoodDelivery.strategy;
import FoodDelivery.model.*;
import java.util.List; import java.util.Optional;
public interface AgentAssignmentStrategy {
    Optional<DeliveryAgent> assign(List<DeliveryAgent> agents, Restaurant restaurant);
    String getName();
}
