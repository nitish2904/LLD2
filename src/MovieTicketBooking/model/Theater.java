package MovieTicketBooking.model;
import java.util.ArrayList;
import java.util.List;
public class Theater {
    private final String theaterId;
    private final String name;
    private final String city;
    private final List<Screen> screens;
    public Theater(String theaterId, String name, String city) {
        this.theaterId = theaterId; this.name = name; this.city = city; this.screens = new ArrayList<>();
    }
    public void addScreen(Screen screen) { screens.add(screen); }
    public String getTheaterId() { return theaterId; }
    public String getName() { return name; }
    public String getCity() { return city; }
    public List<Screen> getScreens() { return screens; }
    @Override public String toString() { return "Theater[" + name + ", " + city + " | " + screens.size() + " screens]"; }
}
