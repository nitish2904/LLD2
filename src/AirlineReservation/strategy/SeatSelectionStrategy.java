package AirlineReservation.strategy;
import AirlineReservation.model.*;
import java.util.List; import java.util.Optional;
public interface SeatSelectionStrategy {
    Optional<Seat> selectSeat(List<Seat> seats, SeatClass seatClass);
    String getName();
}
