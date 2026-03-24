package RideSharing.model;
public class Driver {
    private final String driverId, name, vehiclePlate;
    private Location location;
    private double rating;
    private int totalRides;
    private volatile boolean available;
    public Driver(String driverId, String name, String vehiclePlate, Location location) {
        this.driverId = driverId; this.name = name; this.vehiclePlate = vehiclePlate; this.location = location; this.rating = 5.0; this.totalRides = 0; this.available = true;
    }
    public synchronized boolean assign() { if (!available) return false; available = false; return true; }
    public synchronized void release() { available = true; }
    public void updateRating(double r) { rating = (rating * totalRides + r) / (totalRides + 1); totalRides++; }
    public void setLocation(Location l) { location = l; }
    public String getDriverId() { return driverId; }
    public String getName() { return name; }
    public Location getLocation() { return location; }
    public double getRating() { return rating; }
    public boolean isAvailable() { return available; }
    @Override public String toString() { return name + "(⭐" + String.format("%.1f", rating) + ", " + vehiclePlate + ")"; }
}
