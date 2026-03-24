package ParkingLot.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Singleton ParkingLot — represents the entire parking facility.
 * Thread-safe: double-checked locking with volatile.
 */
public class ParkingLot {
    private static volatile ParkingLot instance;
    private final List<ParkingFloor> floors;
    private final String name;

    private ParkingLot(String name) {
        this.name = name;
        this.floors = new ArrayList<>();
    }

    public static ParkingLot getInstance() {
        if (instance == null) {
            synchronized (ParkingLot.class) {
                if (instance == null) {
                    instance = new ParkingLot("Main Parking Lot");
                }
            }
        }
        return instance;
    }

    /**
     * Reset singleton (for testing / demo scenarios).
     */
    public static synchronized void reset() {
        instance = null;
    }

    public void addFloor(ParkingFloor floor) {
        floors.add(floor);
    }

    public List<ParkingFloor> getFloors() {
        return Collections.unmodifiableList(floors);
    }

    public String getName() {
        return name;
    }

    public int getTotalSpots() {
        return floors.stream().mapToInt(f -> f.getSpots().size()).sum();
    }

    public int getTotalAvailable() {
        return floors.stream().mapToInt(ParkingFloor::getAvailableSpotCount).sum();
    }

    public void printStatus() {
        System.out.println("\n=== " + name + " Status ===");
        for (ParkingFloor floor : floors) {
            System.out.println("  " + floor);
        }
        System.out.println("  Total: " + getTotalAvailable() + "/" + getTotalSpots() + " available");
        System.out.println("=============================\n");
    }
}
