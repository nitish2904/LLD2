package AirlineReservation.strategy;
import AirlineReservation.model.*;
import java.util.*; 
public class WindowPreferenceStrategy implements SeatSelectionStrategy {
    public Optional<Seat> selectSeat(List<Seat> seats, SeatClass sc) {
        Optional<Seat> window = seats.stream().filter(s -> !s.isBooked() && s.getSeatClass() == sc && s.getType() == SeatType.WINDOW).findFirst();
        if (window.isPresent()) return window;
        return seats.stream().filter(s -> !s.isBooked() && s.getSeatClass() == sc).findFirst();
    }
    public String getName() { return "WindowPreference"; }
}
