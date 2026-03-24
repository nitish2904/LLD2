package RideSharing.model;
import java.util.UUID;
public class Ride {
    private final String rideId;
    private final Rider rider;
    private Driver driver;
    private final Location pickup, dropoff;
    private final double fare;
    private RideStatus status;
    public Ride(Rider rider, Location pickup, Location dropoff, double farePerUnit) {
        this.rideId = "RIDE-" + UUID.randomUUID().toString().substring(0,6).toUpperCase();
        this.rider = rider; this.pickup = pickup; this.dropoff = dropoff;
        this.fare = pickup.distanceTo(dropoff) * farePerUnit; this.status = RideStatus.REQUESTED;
    }
    public void matchDriver(Driver d) { this.driver = d; this.status = RideStatus.MATCHED; }
    public void start() { status = RideStatus.IN_PROGRESS; }
    public void complete() { status = RideStatus.COMPLETED; if (driver != null) { driver.setLocation(dropoff); driver.release(); } }
    public void cancel() { status = RideStatus.CANCELLED; if (driver != null) driver.release(); }
    public String getRideId() { return rideId; }
    public Rider getRider() { return rider; }
    public Driver getDriver() { return driver; }
    public Location getPickup() { return pickup; }
    public Location getDropoff() { return dropoff; }
    public double getFare() { return fare; }
    public RideStatus getStatus() { return status; }
    @Override public String toString() { return "Ride[" + rideId + " | " + rider.getName() + " | " + (driver != null ? driver.getName() : "unmatched") + " | " + pickup + "→" + dropoff + " | $" + String.format("%.2f", fare) + " | " + status + "]"; }
}
