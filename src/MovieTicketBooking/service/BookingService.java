package MovieTicketBooking.service;

import MovieTicketBooking.model.*;
import MovieTicketBooking.observer.BookingObserver;
import MovieTicketBooking.strategy.SeatSelectionStrategy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class BookingService {
    private volatile SeatSelectionStrategy strategy;
    private final List<BookingObserver> observers = new CopyOnWriteArrayList<>();
    private final ConcurrentHashMap<String, Booking> bookings = new ConcurrentHashMap<>();

    public BookingService(SeatSelectionStrategy strategy) { this.strategy = strategy; }
    public void setStrategy(SeatSelectionStrategy strategy) { this.strategy = strategy; }
    public void addObserver(BookingObserver observer) { observers.add(observer); }

    public Optional<Booking> bookSeats(Show show, String userId, int numSeats) {
        List<Seat> available = show.getAvailableSeats();
        List<Seat> selected = strategy.selectSeats(available, numSeats);
        if (selected.isEmpty()) {
            System.out.println("[BOOKING] ❌ Not enough seats for " + userId + " in " + show.getMovie().getTitle());
            return Optional.empty();
        }
        // Hold all seats atomically
        List<Seat> held = new ArrayList<>();
        for (Seat seat : selected) {
            if (seat.hold()) { held.add(seat); }
            else { held.forEach(Seat::release); return Optional.empty(); }
        }
        // Book all held seats
        held.forEach(Seat::book);
        Booking booking = new Booking(show, userId, held);
        bookings.put(booking.getBookingId(), booking);
        observers.forEach(o -> o.onBookingConfirmed(booking));
        System.out.println("[BOOKING] ✅ " + booking);
        return Optional.of(booking);
    }

    public boolean cancelBooking(String bookingId) {
        Booking booking = bookings.get(bookingId);
        if (booking == null || booking.getStatus() == BookingStatus.CANCELLED) return false;
        booking.cancel();
        observers.forEach(o -> o.onBookingCancelled(booking));
        System.out.println("[BOOKING] 🚫 Cancelled: " + booking);
        return true;
    }

    public Optional<Booking> getBooking(String bookingId) { return Optional.ofNullable(bookings.get(bookingId)); }
}
