package FoodDelivery.strategy;
import FoodDelivery.model.*;
import java.util.*; 
public class NearestAgentStrategy implements AgentAssignmentStrategy {
    public Optional<DeliveryAgent> assign(List<DeliveryAgent> agents, Restaurant r) {
        return agents.stream().filter(DeliveryAgent::isAvailable).min(Comparator.comparingDouble(a -> a.distanceTo(r.getLat(), r.getLng())));
    }
    public String getName() { return "Nearest"; }
}
