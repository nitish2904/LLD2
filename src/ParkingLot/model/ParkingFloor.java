package ParkingLot.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Represents a floor in the parking lot.
 * Thread-safe: uses ReentrantLock for park/unpark operations and AtomicInteger for availability.
 */
public class ParkingFloor {
    private final int floorNumber;
    private final List<ParkingSpot> spots;
    private final AtomicInteger availableSpotCount;
    private final ReentrantLock lock;

    public ParkingFloor(int floorNumber) {
        this.floorNumber = floorNumber;
        this.spots = new ArrayList<>();
        this.availableSpotCount = new AtomicInteger(0);
        this.lock = new ReentrantLock();
    }

    public void addSpot(ParkingSpot spot) {
        spots.add(spot);
        availableSpotCount.incrementAndGet();
    }

    /**
     * Park a vehicle in the given spot. Must be called under lock.
     */
    public boolean parkVehicle(ParkingSpot spot, Vehicle vehicle) {
        lock.lock();
        try {
            if (spot.park(vehicle)) {
                availableSpotCount.decrementAndGet();
                return true;
            }
            return false;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Unpark a vehicle from the given spot. Must be called under lock.
     */
    public Vehicle unparkVehicle(ParkingSpot spot) {
        lock.lock();
        try {
            Vehicle vehicle = spot.unpark();
            if (vehicle != null) {
                availableSpotCount.incrementAndGet();
            }
            return vehicle;
        } finally {
            lock.unlock();
        }
    }

    /**
     * Find an available spot for the given vehicle on this floor.
     * Iterates through spots; the caller (strategy) decides ordering.
     */
    public Optional<ParkingSpot> findAvailableSpot(Vehicle vehicle) {
        lock.lock();
        try {
            return spots.stream()
                    .filter(ParkingSpot::isAvailable)
                    .filter(s -> s.canFit(vehicle))
                    .findFirst();
        } finally {
            lock.unlock();
        }
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public List<ParkingSpot> getSpots() {
        return Collections.unmodifiableList(spots);
    }

    public int getAvailableSpotCount() {
        return availableSpotCount.get();
    }

    public ReentrantLock getLock() {
        return lock;
    }

    @Override
    public String toString() {
        return "Floor-" + floorNumber + " [available=" + availableSpotCount.get() +
               "/" + spots.size() + "]";
    }
}
