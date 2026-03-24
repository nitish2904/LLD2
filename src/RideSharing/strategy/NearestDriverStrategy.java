package RideSharing.strategy;
import RideSharing.model.*;
import java.util.*;
public class NearestDriverStrategy implements DriverMatchingStrategy {
    public Optional<Driver> findDriver(List<Driver> drivers, Location pickup) {
        return drivers.stream().filter(Driver::isAvailable).min(Comparator.comparingDouble(d -> d.getLocation().distanceTo(pickup)));
    }
    public String getName() { return "Nearest"; }
}
