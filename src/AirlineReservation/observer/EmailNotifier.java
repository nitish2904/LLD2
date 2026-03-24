package AirlineReservation.observer;
import AirlineReservation.model.Booking;
public class EmailNotifier implements BookingObserver {
    public void onBooking(Booking b) { System.out.println("[EMAIL] 📧 Booking confirmed: " + b.getBookingId() + " | " + b.getSeat().getSeatId() + " on " + b.getFlight().getFlightId()); }
    public void onCancellation(Booking b) { System.out.println("[EMAIL] 📧 Booking cancelled: " + b.getBookingId()); }
}
