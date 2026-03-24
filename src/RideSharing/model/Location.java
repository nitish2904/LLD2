package RideSharing.model;
public class Location {
    private final double lat, lng;
    private final String name;
    public Location(double lat, double lng, String name) { this.lat = lat; this.lng = lng; this.name = name; }
    public double distanceTo(Location o) { return Math.sqrt(Math.pow(lat-o.lat,2)+Math.pow(lng-o.lng,2)); }
    public double getLat() { return lat; }
    public double getLng() { return lng; }
    @Override public String toString() { return name; }
}
