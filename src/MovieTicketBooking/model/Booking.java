package MovieTicketBooking.model;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
public class Booking {
    private final String bookingId;
    private final Show show;
    private final String userId;
    private final List<Seat> seats;
    private final double totalAmount;
    private final LocalDateTime bookingTime;
    private BookingStatus status;
    public Booking(Show show, String userId, List<Seat> seats) {
        this.bookingId = "BK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.show = show; this.userId = userId; this.seats = seats;
        this.totalAmount = seats.stream().mapToDouble(Seat::getPrice).sum();
        this.bookingTime = LocalDateTime.now(); this.status = BookingStatus.CONFIRMED;
    }
    public void cancel() {
        this.status = BookingStatus.CANCELLED;
        seats.forEach(Seat::cancel);
    }
    public String getBookingId() { return bookingId; }
    public Show getShow() { return show; }
    public String getUserId() { return userId; }
    public List<Seat> getSeats() { return Collections.unmodifiableList(seats); }
    public double getTotalAmount() { return totalAmount; }
    public BookingStatus getStatus() { return status; }
    public LocalDateTime getBookingTime() { return bookingTime; }
    @Override public String toString() {
        return "Booking[" + bookingId + " | " + show.getMovie().getTitle() + " | seats=" + seats.size() + " | $" + String.format("%.2f", totalAmount) + " | " + status + "]";
    }
}
