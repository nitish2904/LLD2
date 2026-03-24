package ParkingLot.observer;

import ParkingLot.model.ParkingFloor;
import ParkingLot.model.ParkingSpot;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Thread-safe availability tracker using ReadWriteLock.
 * Many readers (check availability), few writers (spot changes).
 */
public class AvailabilityTracker implements ParkingLotObserver {
    private final Map<Integer, Integer> floorAvailability;
    private final ReadWriteLock rwLock;

    public AvailabilityTracker() {
        this.floorAvailability = new ConcurrentHashMap<>();
        this.rwLock = new ReentrantReadWriteLock();
    }

    public void initFloor(int floorNumber, int totalSpots) {
        rwLock.writeLock().lock();
        try {
            floorAvailability.put(floorNumber, totalSpots);
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    @Override
    public void onSpotTaken(ParkingFloor floor, ParkingSpot spot) {
        rwLock.writeLock().lock();
        try {
            floorAvailability.merge(floor.getFloorNumber(), -1, Integer::sum);
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    @Override
    public void onSpotFreed(ParkingFloor floor, ParkingSpot spot) {
        rwLock.writeLock().lock();
        try {
            floorAvailability.merge(floor.getFloorNumber(), 1, Integer::sum);
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    /**
     * Returns an unmodifiable snapshot of floor availability.
     */
    public Map<Integer, Integer> getAvailability() {
        rwLock.readLock().lock();
        try {
            return Collections.unmodifiableMap(new ConcurrentHashMap<>(floorAvailability));
        } finally {
            rwLock.readLock().unlock();
        }
    }

    public int getAvailableOnFloor(int floorNumber) {
        rwLock.readLock().lock();
        try {
            return floorAvailability.getOrDefault(floorNumber, 0);
        } finally {
            rwLock.readLock().unlock();
        }
    }

    @Override
    public String toString() {
        return "AvailabilityTracker" + getAvailability();
    }
}
