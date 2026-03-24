package FoodDelivery.model;
public class DeliveryAgent {
    private final String id, name; private double lat, lng, rating; private int trips; private volatile boolean available;
    public DeliveryAgent(String id, String name, double lat, double lng) { this.id = id; this.name = name; this.lat = lat; this.lng = lng; this.rating = 5.0; this.available = true; }
    public synchronized boolean assign() { if (!available) return false; available = false; return true; }
    public synchronized void release() { available = true; }
    public void updateRating(double r) { rating = (rating * trips + r) / (trips + 1); trips++; }
    public void moveTo(double lat, double lng) { this.lat = lat; this.lng = lng; }
    public double distanceTo(double lat, double lng) { return Math.sqrt(Math.pow(this.lat-lat,2)+Math.pow(this.lng-lng,2)); }
    public String getName() { return name; }
    public double getRating() { return rating; }
    public boolean isAvailable() { return available; }
    @Override public String toString() { return name + "(⭐" + String.format("%.1f", rating) + ")"; }
}
