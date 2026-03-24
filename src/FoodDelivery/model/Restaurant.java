package FoodDelivery.model;
import java.util.*;
public class Restaurant {
    private final String id, name; private final double lat, lng; private final List<MenuItem> menu = new ArrayList<>();
    public Restaurant(String id, String name, double lat, double lng) { this.id = id; this.name = name; this.lat = lat; this.lng = lng; }
    public void addItem(MenuItem item) { menu.add(item); }
    public String getId() { return id; }
    public String getName() { return name; }
    public double getLat() { return lat; }
    public double getLng() { return lng; }
    public List<MenuItem> getMenu() { return menu; }
    @Override public String toString() { return name; }
}
