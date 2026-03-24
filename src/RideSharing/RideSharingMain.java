package RideSharing;
import RideSharing.model.*;
import RideSharing.observer.NotificationService;
import RideSharing.service.RideService;
import RideSharing.strategy.*;
import java.util.Optional;
public class RideSharingMain {
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║   🚗 RIDE-SHARING SYSTEM DEMO     ║");
        System.out.println("╚════════════════════════════════════╝\n");
        RideService service = new RideService(new NearestDriverStrategy(), 2.5);
        service.addObserver(new NotificationService());
        Location downtown = new Location(0, 0, "Downtown");
        Location airport = new Location(10, 10, "Airport");
        Location mall = new Location(3, 4, "Mall");
        Location park = new Location(1, 1, "Park");
        service.addDriver(new Driver("D1", "Dave", "KA-01-1234", new Location(1, 0, "NearDowntown")));
        service.addDriver(new Driver("D2", "Eve", "KA-02-5678", new Location(8, 9, "NearAirport")));
        service.addDriver(new Driver("D3", "Frank", "KA-03-9999", new Location(2, 3, "NearMall")));
        Rider alice = new Rider("R1", "Alice");
        Rider bob = new Rider("R2", "Bob");

        System.out.println("═══ SCENARIO 1: Nearest driver ═══\n");
        Optional<Ride> r1 = service.requestRide(alice, downtown, airport);
        if (r1.isPresent()) { service.startRide(r1.get().getRideId()); service.completeRide(r1.get().getRideId(), 4.5); }

        System.out.println("\n═══ SCENARIO 2: Highest rated driver ═══\n");
        service.setStrategy(new HighestRatedDriverStrategy());
        Optional<Ride> r2 = service.requestRide(bob, mall, park);
        if (r2.isPresent()) { service.startRide(r2.get().getRideId()); service.completeRide(r2.get().getRideId(), 5.0); }

        System.out.println("\n═══ SCENARIO 3: Cancel ride ═══\n");
        service.setStrategy(new NearestDriverStrategy());
        Optional<Ride> r3 = service.requestRide(alice, park, airport);
        if (r3.isPresent()) service.cancelRide(r3.get().getRideId());

        System.out.println("\n═══ SCENARIO 4: All drivers busy ═══\n");
        service.requestRide(alice, downtown, mall);
        service.requestRide(bob, downtown, mall);
        service.requestRide(new Rider("R3", "Charlie"), downtown, airport);
        service.requestRide(new Rider("R4", "Diana"), downtown, airport); // should fail

        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║   ✅ ALL SCENARIOS COMPLETE         ║");
        System.out.println("╚════════════════════════════════════╝");
    }
}
