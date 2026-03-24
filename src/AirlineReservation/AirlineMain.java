package AirlineReservation;
import AirlineReservation.model.*;
import AirlineReservation.observer.EmailNotifier;
import AirlineReservation.service.AirlineService;
import AirlineReservation.strategy.*;
import java.time.LocalDateTime;
import java.util.Optional;
public class AirlineMain {
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║   ✈️ AIRLINE RESERVATION DEMO     ║");
        System.out.println("╚════════════════════════════════════╝\n");
        AirlineService airline = new AirlineService(new WindowPreferenceStrategy());
        airline.addObserver(new EmailNotifier());
        Flight f1 = new Flight("AI-101", "BLR", "DEL", LocalDateTime.now().plusDays(1));
        f1.addSeat(new Seat("1A", SeatType.WINDOW, SeatClass.FIRST, 500));
        f1.addSeat(new Seat("1B", SeatType.AISLE, SeatClass.FIRST, 480));
        f1.addSeat(new Seat("10A", SeatType.WINDOW, SeatClass.ECONOMY, 120));
        f1.addSeat(new Seat("10B", SeatType.MIDDLE, SeatClass.ECONOMY, 100));
        f1.addSeat(new Seat("10C", SeatType.AISLE, SeatClass.ECONOMY, 110));
        f1.addSeat(new Seat("11A", SeatType.WINDOW, SeatClass.ECONOMY, 125));
        airline.addFlight(f1);
        airline.showAvailability("AI-101");

        System.out.println("\n═══ SCENARIO 1: Window preference ═══\n");
        Optional<Booking> b1 = airline.bookSeat("Alice", "AI-101", SeatClass.ECONOMY);
        Optional<Booking> b2 = airline.bookSeat("Bob", "AI-101", SeatClass.FIRST);

        System.out.println("\n═══ SCENARIO 2: Cheapest seat ═══\n");
        airline.setStrategy(new CheapestSeatStrategy());
        Optional<Booking> b3 = airline.bookSeat("Charlie", "AI-101", SeatClass.ECONOMY);

        System.out.println("\n═══ SCENARIO 3: Cancel & rebook ═══\n");
        if (b1.isPresent()) airline.cancelBooking(b1.get().getBookingId());
        airline.setStrategy(new WindowPreferenceStrategy());
        airline.bookSeat("Diana", "AI-101", SeatClass.ECONOMY);

        System.out.println("\n═══ SCENARIO 4: Full class ═══\n");
        airline.bookSeat("Eve", "AI-101", SeatClass.ECONOMY);
        airline.bookSeat("Frank", "AI-101", SeatClass.ECONOMY);
        airline.showAvailability("AI-101");

        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║   ✅ ALL SCENARIOS COMPLETE         ║");
        System.out.println("╚════════════════════════════════════╝");
    }
}
