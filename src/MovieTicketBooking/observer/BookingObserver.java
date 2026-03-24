package MovieTicketBooking.observer;
import MovieTicketBooking.model.Booking;
public interface BookingObserver {
    void onBookingConfirmed(Booking booking);
    void onBookingCancelled(Booking booking);
}
