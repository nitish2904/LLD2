package RideSharing.strategy;
import RideSharing.model.*;
import java.util.List;
import java.util.Optional;
public interface DriverMatchingStrategy {
    Optional<Driver> findDriver(List<Driver> drivers, Location pickup);
    String getName();
}
