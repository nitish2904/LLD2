package MovieTicketBooking.observer;
import MovieTicketBooking.model.Booking;
public class EmailNotifier implements BookingObserver {
    @Override
    public void onBookingConfirmed(Booking booking) {
        System.out.println("[EMAIL] 📧 Booking confirmed: " + booking.getBookingId() + " for " + booking.getUserId() + " | " + booking.getShow().getMovie().getTitle() + " | $" + String.format("%.2f", booking.getTotalAmount()));
    }
    @Override
    public void onBookingCancelled(Booking booking) {
        System.out.println("[EMAIL] 📧 Booking cancelled: " + booking.getBookingId() + " | Refund: $" + String.format("%.2f", booking.getTotalAmount()));
    }
}
