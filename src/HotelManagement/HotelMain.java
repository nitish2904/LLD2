package HotelManagement;
import HotelManagement.model.*;
import HotelManagement.observer.EmailNotifier;
import HotelManagement.service.HotelService;
import HotelManagement.strategy.*;
import java.time.LocalDate;
import java.util.Optional;
public class HotelMain {
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║   🏨 HOTEL MANAGEMENT DEMO        ║");
        System.out.println("╚════════════════════════════════════╝\n");
        HotelService hotel = new HotelService(new CheapestRoomStrategy());
        hotel.addObserver(new EmailNotifier());
        hotel.addRoom(new Room("101", RoomType.SINGLE, 80, 1));
        hotel.addRoom(new Room("201", RoomType.SINGLE, 90, 2));
        hotel.addRoom(new Room("301", RoomType.DOUBLE, 150, 3));
        hotel.addRoom(new Room("401", RoomType.DOUBLE, 170, 4));
        hotel.addRoom(new Room("501", RoomType.SUITE, 350, 5));
        Guest g1 = new Guest("G1", "Alice", "alice@email.com", "555-0001");
        Guest g2 = new Guest("G2", "Bob", "bob@email.com", "555-0002");
        Guest g3 = new Guest("G3", "Charlie", "charlie@email.com", "555-0003");

        System.out.println("═══ SCENARIO 1: Book cheapest Single ═══\n");
        Optional<Reservation> r1 = hotel.makeReservation(g1, RoomType.SINGLE, LocalDate.now(), LocalDate.now().plusDays(3));

        System.out.println("\n═══ SCENARIO 2: Book highest-floor Double ═══\n");
        hotel.setStrategy(new HighestFloorStrategy());
        Optional<Reservation> r2 = hotel.makeReservation(g2, RoomType.DOUBLE, LocalDate.now(), LocalDate.now().plusDays(2));

        System.out.println("\n═══ SCENARIO 3: Check-in & Check-out ═══\n");
        if (r1.isPresent()) { hotel.checkIn(r1.get().getReservationId()); hotel.checkOut(r1.get().getReservationId()); }

        System.out.println("\n═══ SCENARIO 4: Cancel reservation ═══\n");
        if (r2.isPresent()) hotel.cancelReservation(r2.get().getReservationId());

        System.out.println("\n═══ SCENARIO 5: No rooms left ═══\n");
        hotel.setStrategy(new CheapestRoomStrategy());
        hotel.makeReservation(g3, RoomType.SUITE, LocalDate.now(), LocalDate.now().plusDays(1));
        hotel.makeReservation(g3, RoomType.SUITE, LocalDate.now(), LocalDate.now().plusDays(1));

        System.out.println("\nRoom Status:"); hotel.printRooms();
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║   ✅ ALL SCENARIOS COMPLETE         ║");
        System.out.println("╚════════════════════════════════════╝");
    }
}
