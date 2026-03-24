package ParkingLot.strategy;

import ParkingLot.model.ParkingFloor;
import ParkingLot.model.ParkingSpot;
import ParkingLot.model.Vehicle;

import java.util.List;
import java.util.Optional;

/**
 * Selects the first available spot that fits the vehicle, scanning floor by floor.
 * Simple and fast — O(n) scan, returns immediately on first match.
 */
public class FirstAvailableStrategy implements ParkingStrategy {

    @Override
    public Optional<ParkingSpot> findSpot(List<ParkingFloor> floors, Vehicle vehicle) {
        for (ParkingFloor floor : floors) {
            Optional<ParkingSpot> spot = floor.findAvailableSpot(vehicle);
            if (spot.isPresent()) {
                return spot;
            }
        }
        return Optional.empty();
    }
}
