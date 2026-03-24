package MovieTicketBooking.strategy;
import MovieTicketBooking.model.Seat;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
public class CheapestFirstStrategy implements SeatSelectionStrategy {
    @Override
    public List<Seat> selectSeats(List<Seat> availableSeats, int count) {
        if (availableSeats.size() < count) return List.of();
        return availableSeats.stream().sorted(Comparator.comparingDouble(Seat::getPrice))
            .limit(count).collect(Collectors.toList());
    }
}
