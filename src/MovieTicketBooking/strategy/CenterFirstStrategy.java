package MovieTicketBooking.strategy;
import MovieTicketBooking.model.Seat;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
public class CenterFirstStrategy implements SeatSelectionStrategy {
    @Override
    public List<Seat> selectSeats(List<Seat> availableSeats, int count) {
        if (availableSeats.size() < count) return List.of();
        int maxCol = availableSeats.stream().mapToInt(Seat::getCol).max().orElse(0);
        double center = maxCol / 2.0;
        int midRow = availableSeats.stream().mapToInt(Seat::getRow).max().orElse(0) / 2;
        return availableSeats.stream()
            .sorted(Comparator.comparingDouble((Seat s) -> Math.abs(s.getRow() - midRow))
                .thenComparingDouble(s -> Math.abs(s.getCol() - center)))
            .limit(count).collect(Collectors.toList());
    }
}
