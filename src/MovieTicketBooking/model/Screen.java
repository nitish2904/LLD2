package MovieTicketBooking.model;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
public class Screen {
    private final String screenId;
    private final String name;
    private final List<Seat> seats;
    public Screen(String screenId, String name) {
        this.screenId = screenId; this.name = name; this.seats = new ArrayList<>();
    }
    public void addSeat(Seat seat) { seats.add(seat); }
    public String getScreenId() { return screenId; }
    public String getName() { return name; }
    public List<Seat> getSeats() { return Collections.unmodifiableList(seats); }
    public int getTotalSeats() { return seats.size(); }
    public long getAvailableCount() { return seats.stream().filter(Seat::isAvailable).count(); }
    @Override public String toString() { return "Screen[" + name + " | " + getAvailableCount() + "/" + getTotalSeats() + " available]"; }
}
