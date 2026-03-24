package RideSharing.service;
import RideSharing.model.*;
import RideSharing.observer.RideObserver;
import RideSharing.strategy.DriverMatchingStrategy;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
public class RideService {
    private final List<Driver> drivers = new ArrayList<>();
    private final Map<String, Ride> rides = new LinkedHashMap<>();
    private final List<RideObserver> observers = new CopyOnWriteArrayList<>();
    private volatile DriverMatchingStrategy strategy;
    private final double farePerUnit;
    public RideService(DriverMatchingStrategy strategy, double farePerUnit) { this.strategy = strategy; this.farePerUnit = farePerUnit; }
    public void addDriver(Driver d) { drivers.add(d); }
    public void setStrategy(DriverMatchingStrategy s) { this.strategy = s; System.out.println("[RIDE] Strategy: " + s.getName()); }
    public void addObserver(RideObserver o) { observers.add(o); }
    public Optional<Ride> requestRide(Rider rider, Location pickup, Location dropoff) {
        Ride ride = new Ride(rider, pickup, dropoff, farePerUnit);
        observers.forEach(o -> o.onRideRequested(ride));
        Optional<Driver> driverOpt = strategy.findDriver(drivers, pickup);
        if (driverOpt.isEmpty() || !driverOpt.get().assign()) { System.out.println("[RIDE] ❌ No driver available"); ride.cancel(); return Optional.empty(); }
        ride.matchDriver(driverOpt.get());
        rides.put(ride.getRideId(), ride);
        observers.forEach(o -> o.onRideMatched(ride));
        System.out.println("[RIDE] ✅ " + ride); return Optional.of(ride);
    }
    public void startRide(String rideId) { Ride r = rides.get(rideId); if (r != null) { r.start(); System.out.println("[RIDE] 🚗 Started: " + r); } }
    public void completeRide(String rideId, double rating) {
        Ride r = rides.get(rideId);
        if (r != null) { r.complete(); r.getDriver().updateRating(rating); observers.forEach(o -> o.onRideCompleted(r)); System.out.println("[RIDE] 🏁 " + r); }
    }
    public void cancelRide(String rideId) { Ride r = rides.get(rideId); if (r != null) { r.cancel(); observers.forEach(o -> o.onRideCancelled(r)); System.out.println("[RIDE] 🚫 " + r); } }
}
