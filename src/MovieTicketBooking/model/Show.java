package MovieTicketBooking.model;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
public class Show {
    private final String showId;
    private final Movie movie;
    private final Screen screen;
    private final LocalDateTime startTime;
    public Show(String showId, Movie movie, Screen screen, LocalDateTime startTime) {
        this.showId = showId; this.movie = movie; this.screen = screen; this.startTime = startTime;
    }
    public String getShowId() { return showId; }
    public Movie getMovie() { return movie; }
    public Screen getScreen() { return screen; }
    public LocalDateTime getStartTime() { return startTime; }
    public List<Seat> getAvailableSeats() {
        return screen.getSeats().stream().filter(Seat::isAvailable).collect(Collectors.toList());
    }
    @Override public String toString() {
        return "Show[" + showId + " | " + movie.getTitle() + " | " + screen.getName() + " | " + startTime + " | " + getAvailableSeats().size() + " seats free]";
    }
}
