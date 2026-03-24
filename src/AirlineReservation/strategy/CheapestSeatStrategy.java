package AirlineReservation.strategy;
import AirlineReservation.model.*;
import java.util.*;
public class CheapestSeatStrategy implements SeatSelectionStrategy {
    public Optional<Seat> selectSeat(List<Seat> seats, SeatClass sc) {
        return seats.stream().filter(s -> !s.isBooked() && s.getSeatClass() == sc).min(Comparator.comparingDouble(Seat::getPrice));
    }
    public String getName() { return "Cheapest"; }
}
