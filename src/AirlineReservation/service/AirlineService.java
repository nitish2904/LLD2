package AirlineReservation.service;
import AirlineReservation.model.*;
import AirlineReservation.observer.BookingObserver;
import AirlineReservation.strategy.SeatSelectionStrategy;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
public class AirlineService {
    private final Map<String, Flight> flights = new LinkedHashMap<>();
    private final Map<String, Booking> bookings = new LinkedHashMap<>();
    private final List<BookingObserver> observers = new CopyOnWriteArrayList<>();
    private volatile SeatSelectionStrategy strategy;
    public AirlineService(SeatSelectionStrategy strategy) { this.strategy = strategy; }
    public void addFlight(Flight f) { flights.put(f.getFlightId(), f); }
    public void setStrategy(SeatSelectionStrategy s) { this.strategy = s; System.out.println("[AIR] Strategy: " + s.getName()); }
    public void addObserver(BookingObserver o) { observers.add(o); }
    public Optional<Booking> bookSeat(String passengerId, String flightId, SeatClass sc) {
        Flight flight = flights.get(flightId);
        if (flight == null) { System.out.println("[AIR] ❌ Flight not found"); return Optional.empty(); }
        Optional<Seat> seatOpt = strategy.selectSeat(flight.getSeats(), sc);
        if (seatOpt.isEmpty() || !seatOpt.get().book()) { System.out.println("[AIR] ❌ No " + sc + " seat available on " + flightId); return Optional.empty(); }
        Booking booking = new Booking(passengerId, flight, seatOpt.get());
        bookings.put(booking.getBookingId(), booking);
        observers.forEach(o -> o.onBooking(booking));
        System.out.println("[AIR] ✅ " + booking); return Optional.of(booking);
    }
    public void cancelBooking(String bookingId) {
        Booking b = bookings.get(bookingId);
        if (b != null) { b.cancel(); observers.forEach(o -> o.onCancellation(b)); System.out.println("[AIR] 🚫 " + b); }
    }
    public void showAvailability(String flightId) {
        Flight f = flights.get(flightId);
        long open = f.getSeats().stream().filter(s -> !s.isBooked()).count();
        System.out.println(f + " | Available: " + open + "/" + f.getSeats().size());
    }
}
