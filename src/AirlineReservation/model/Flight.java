package AirlineReservation.model;
import java.time.LocalDateTime;
import java.util.*;
public class Flight {
    private final String flightId, from, to; private final LocalDateTime departure; private final List<Seat> seats = new ArrayList<>();
    public Flight(String flightId, String from, String to, LocalDateTime departure) { this.flightId = flightId; this.from = from; this.to = to; this.departure = departure; }
    public void addSeat(Seat s) { seats.add(s); }
    public List<Seat> getSeats() { return seats; }
    public String getFlightId() { return flightId; }
    public String getFrom() { return from; }
    public String getTo() { return to; }
    @Override public String toString() { return "Flight[" + flightId + " | " + from + "→" + to + " | " + departure.toLocalDate() + " | seats=" + seats.size() + "]"; }
}
