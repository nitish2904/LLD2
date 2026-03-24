package ParkingLot;

import ParkingLot.factory.ParkingSpotFactory;
import ParkingLot.factory.VehicleFactory;
import ParkingLot.model.*;
import ParkingLot.observer.AvailabilityTracker;
import ParkingLot.observer.DisplayBoard;
import ParkingLot.service.ParkingService;
import ParkingLot.service.PaymentService;
import ParkingLot.service.TicketService;
import ParkingLot.strategy.FirstAvailableStrategy;
import ParkingLot.strategy.NearestToEntranceStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

/**
 * Demo: Parking Lot System
 * 
 * Scenarios:
 *   1. Sequential: Park cars, motorcycles, trucks with NearestToEntrance strategy
 *   2. Sequential: Switch to FirstAvailable strategy, park more vehicles
 *   3. Sequential: Unpark vehicles, verify payments
 *   4. Concurrent: 10 vehicles arrive simultaneously from different entrances
 */
public class ParkingLotMain {

    public static void main(String[] args) throws Exception {
        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║      🅿️  PARKING LOT SYSTEM DEMO          ║");
        System.out.println("╚════════════════════════════════════════════╝\n");

        // === Setup ===
        ParkingLot.reset();
        ParkingLot lot = ParkingLot.getInstance();

        // Create 2 floors with mixed spot types
        ParkingFloor floor1 = new ParkingFloor(1);
        floor1.addSpot(ParkingSpotFactory.create(SpotType.COMPACT, "1-C1", 1, 1));
        floor1.addSpot(ParkingSpotFactory.create(SpotType.COMPACT, "1-C2", 1, 2));
        floor1.addSpot(ParkingSpotFactory.create(SpotType.REGULAR, "1-R1", 1, 3));
        floor1.addSpot(ParkingSpotFactory.create(SpotType.REGULAR, "1-R2", 1, 4));
        floor1.addSpot(ParkingSpotFactory.create(SpotType.LARGE, "1-L1", 1, 5));

        ParkingFloor floor2 = new ParkingFloor(2);
        floor2.addSpot(ParkingSpotFactory.create(SpotType.COMPACT, "2-C1", 2, 1));
        floor2.addSpot(ParkingSpotFactory.create(SpotType.REGULAR, "2-R1", 2, 2));
        floor2.addSpot(ParkingSpotFactory.create(SpotType.REGULAR, "2-R2", 2, 3));
        floor2.addSpot(ParkingSpotFactory.create(SpotType.LARGE, "2-L1", 2, 4));
        floor2.addSpot(ParkingSpotFactory.create(SpotType.LARGE, "2-L2", 2, 5));

        lot.addFloor(floor1);
        lot.addFloor(floor2);

        // Services
        TicketService ticketService = new TicketService();
        PaymentService paymentService = new PaymentService(5.0); // $5/hour
        ParkingService parkingService = new ParkingService(
                new NearestToEntranceStrategy(), ticketService, paymentService);

        // Observers
        DisplayBoard displayBoard = new DisplayBoard();
        AvailabilityTracker tracker = new AvailabilityTracker();
        tracker.initFloor(1, 5);
        tracker.initFloor(2, 5);
        parkingService.addObserver(displayBoard);
        parkingService.addObserver(tracker);

        // ============================================
        // SCENARIO 1: Sequential — NearestToEntrance
        // ============================================
        System.out.println("═══ SCENARIO 1: Sequential Parking (NearestToEntrance) ═══\n");
        lot.printStatus();

        Vehicle car1 = VehicleFactory.create(VehicleType.CAR, "CAR-001");
        Vehicle bike1 = VehicleFactory.create(VehicleType.MOTORCYCLE, "BIKE-001");
        Vehicle truck1 = VehicleFactory.create(VehicleType.TRUCK, "TRUCK-001");

        Optional<Ticket> ticket1 = parkingService.parkVehicle(lot, car1);
        Optional<Ticket> ticket2 = parkingService.parkVehicle(lot, bike1);
        Optional<Ticket> ticket3 = parkingService.parkVehicle(lot, truck1);

        lot.printStatus();
        System.out.println("Availability: " + tracker.getAvailability());

        // ============================================
        // SCENARIO 2: Switch to FirstAvailable strategy
        // ============================================
        System.out.println("\n═══ SCENARIO 2: Switch Strategy to FirstAvailable ═══\n");
        parkingService.setStrategy(new FirstAvailableStrategy());

        Vehicle car2 = VehicleFactory.create(VehicleType.CAR, "CAR-002");
        Vehicle bike2 = VehicleFactory.create(VehicleType.MOTORCYCLE, "BIKE-002");
        parkingService.parkVehicle(lot, car2);
        parkingService.parkVehicle(lot, bike2);

        lot.printStatus();

        // ============================================
        // SCENARIO 3: Unpark and Payment
        // ============================================
        System.out.println("\n═══ SCENARIO 3: Unpark & Payment ═══\n");

        // Simulate some time passing (for payment calculation)
        Thread.sleep(100);

        if (ticket1.isPresent()) {
            Payment payment = parkingService.unparkVehicle(lot, ticket1.get());
        }
        if (ticket3.isPresent()) {
            Payment payment = parkingService.unparkVehicle(lot, ticket3.get());
        }

        lot.printStatus();
        System.out.println("Availability: " + tracker.getAvailability());

        // ============================================
        // SCENARIO 4: Concurrent Parking (10 vehicles)
        // ============================================
        System.out.println("\n═══ SCENARIO 4: Concurrent Parking (10 vehicles) ═══\n");

        // Reset for clean concurrent test
        ParkingLot.reset();
        ParkingLot lot2 = ParkingLot.getInstance();

        ParkingFloor cFloor1 = new ParkingFloor(1);
        for (int i = 1; i <= 5; i++) {
            cFloor1.addSpot(ParkingSpotFactory.create(SpotType.REGULAR, "1-R" + i, 1, i));
        }
        ParkingFloor cFloor2 = new ParkingFloor(2);
        for (int i = 1; i <= 5; i++) {
            cFloor2.addSpot(ParkingSpotFactory.create(SpotType.REGULAR, "2-R" + i, 2, i));
        }
        lot2.addFloor(cFloor1);
        lot2.addFloor(cFloor2);

        TicketService ticketService2 = new TicketService();
        PaymentService paymentService2 = new PaymentService(5.0);
        ParkingService parkingService2 = new ParkingService(
                new NearestToEntranceStrategy(), ticketService2, paymentService2);
        parkingService2.addObserver(new DisplayBoard());

        lot2.printStatus();

        // 10 threads try to park simultaneously
        ExecutorService executor = Executors.newFixedThreadPool(10);
        List<Future<Optional<Ticket>>> futures = new ArrayList<>();

        for (int i = 0; i < 12; i++) { // 12 vehicles, only 10 spots
            final int idx = i;
            futures.add(executor.submit(() -> {
                Vehicle v = VehicleFactory.create(VehicleType.CAR, "CONC-" + String.format("%03d", idx));
                return parkingService2.parkVehicle(lot2, v);
            }));
        }

        // Collect results
        int parked = 0, rejected = 0;
        for (Future<Optional<Ticket>> f : futures) {
            Optional<Ticket> t = f.get();
            if (t.isPresent()) parked++;
            else rejected++;
        }

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        lot2.printStatus();
        System.out.println("Concurrent result: " + parked + " parked, " + rejected + " rejected (expected: 10 parked, 2 rejected)\n");

        System.out.println("╔════════════════════════════════════════════╗");
        System.out.println("║      ✅  ALL SCENARIOS COMPLETE            ║");
        System.out.println("╚════════════════════════════════════════════╝");
    }
}
