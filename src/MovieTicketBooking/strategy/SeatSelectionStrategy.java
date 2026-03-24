package MovieTicketBooking.strategy;
import MovieTicketBooking.model.Seat;
import java.util.List;
public interface SeatSelectionStrategy {
    List<Seat> selectSeats(List<Seat> availableSeats, int count);
}
