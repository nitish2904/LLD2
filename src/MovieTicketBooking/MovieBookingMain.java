package MovieTicketBooking;

import MovieTicketBooking.model.*;
import MovieTicketBooking.observer.EmailNotifier;
import MovieTicketBooking.service.BookingService;
import MovieTicketBooking.strategy.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.*;

public class MovieBookingMain {
    public static void main(String[] args) throws Exception {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║  🎬 MOVIE TICKET BOOKING SYSTEM DEMO      ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        // Setup
        Movie movie = new Movie("M1", "Inception", "Sci-Fi", 148);
        Screen screen = new Screen("S1", "IMAX-1");
        for (int r = 1; r <= 5; r++)
            for (int c = 1; c <= 8; c++) {
                SeatType type = r <= 2 ? SeatType.VIP : r <= 4 ? SeatType.PREMIUM : SeatType.REGULAR;
                double price = type == SeatType.VIP ? 25.0 : type == SeatType.PREMIUM ? 18.0 : 12.0;
                screen.addSeat(new Seat("R" + r + "C" + c, r, c, type, price));
            }
        Theater theater = new Theater("T1", "PVR Cinemas", "Bangalore");
        theater.addScreen(screen);
        Show show = new Show("SH1", movie, screen, LocalDateTime.now().plusHours(3));
        BookingService bookingService = new BookingService(new CenterFirstStrategy());
        bookingService.addObserver(new EmailNotifier());

        // Scenario 1: Book with CenterFirst
        System.out.println("═══ SCENARIO 1: Book 3 seats (CenterFirst) ═══\n");
        System.out.println(show);
        Optional<Booking> b1 = bookingService.bookSeats(show, "user1", 3);

        // Scenario 2: Switch to CheapestFirst
        System.out.println("\n═══ SCENARIO 2: Book 2 seats (CheapestFirst) ═══\n");
        bookingService.setStrategy(new CheapestFirstStrategy());
        Optional<Booking> b2 = bookingService.bookSeats(show, "user2", 2);

        // Scenario 3: Cancel booking
        System.out.println("\n═══ SCENARIO 3: Cancel first booking ═══\n");
        if (b1.isPresent()) bookingService.cancelBooking(b1.get().getBookingId());
        System.out.println(show);

        // Scenario 4: Concurrent bookings (10 users, 3 seats each, only ~40 available)
        System.out.println("\n═══ SCENARIO 4: Concurrent Booking (10 users × 3 seats) ═══\n");
        Screen screen2 = new Screen("S2", "Screen-2");
        for (int r = 1; r <= 3; r++)
            for (int c = 1; c <= 5; c++)
                screen2.addSeat(new Seat("R" + r + "C" + c, r, c, SeatType.REGULAR, 10.0));
        Show show2 = new Show("SH2", movie, screen2, LocalDateTime.now().plusHours(5));
        BookingService bs2 = new BookingService(new CenterFirstStrategy());
        bs2.addObserver(new EmailNotifier());

        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Future<Optional<Booking>>> futures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            final String uid = "user" + i;
            futures.add(executor.submit(() -> bs2.bookSeats(show2, uid, 3)));
        }
        int ok = 0, fail = 0;
        for (Future<Optional<Booking>> f : futures) { if (f.get().isPresent()) ok++; else fail++; }
        executor.shutdown();
        System.out.println("\nConcurrent: " + ok + " booked, " + fail + " failed (15 seats / 3 per booking = max 5 bookings)");

        System.out.println("\n╔════════════════════════════════════════════╗");
        System.out.println("║      ✅ ALL SCENARIOS COMPLETE              ║");
        System.out.println("╚════════════════════════════════════════════╝");
    }
}
