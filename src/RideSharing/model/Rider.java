package RideSharing.model;
public class Rider {
    private final String riderId, name;
    public Rider(String riderId, String name) { this.riderId = riderId; this.name = name; }
    public String getRiderId() { return riderId; }
    public String getName() { return name; }
    @Override public String toString() { return name; }
}
