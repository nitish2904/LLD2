package RideSharing.strategy;
import RideSharing.model.*;
import java.util.*;
public class HighestRatedDriverStrategy implements DriverMatchingStrategy {
    public Optional<Driver> findDriver(List<Driver> drivers, Location pickup) {
        return drivers.stream().filter(Driver::isAvailable).max(Comparator.comparingDouble(Driver::getRating));
    }
    public String getName() { return "HighestRated"; }
}
