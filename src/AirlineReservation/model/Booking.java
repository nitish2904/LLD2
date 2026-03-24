package AirlineReservation.model;
import java.util.UUID;
public class Booking {
    public enum Status { CONFIRMED, CANCELLED }
    private final String bookingId, passengerId; private final Flight flight; private final Seat seat; private Status status;
    public Booking(String passengerId, Flight flight, Seat seat) {
        this.bookingId = "BK-" + UUID.randomUUID().toString().substring(0,6).toUpperCase();
        this.passengerId = passengerId; this.flight = flight; this.seat = seat; this.status = Status.CONFIRMED;
    }
    public void cancel() { status = Status.CANCELLED; seat.release(); }
    public String getBookingId() { return bookingId; }
    public String getPassengerId() { return passengerId; }
    public Flight getFlight() { return flight; }
    public Seat getSeat() { return seat; }
    public Status getStatus() { return status; }
    @Override public String toString() { return "Booking[" + bookingId + " | " + passengerId + " | " + flight.getFlightId() + " | " + seat.getSeatId() + " | $" + String.format("%.0f", seat.getPrice()) + " | " + status + "]"; }
}
