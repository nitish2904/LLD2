package ParkingLot.strategy;

import ParkingLot.model.ParkingFloor;
import ParkingLot.model.ParkingSpot;
import ParkingLot.model.Vehicle;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * Selects the available spot closest to the entrance across all floors.
 * Prefers lower floors first, then closest distance within a floor.
 */
public class NearestToEntranceStrategy implements ParkingStrategy {

    @Override
    public Optional<ParkingSpot> findSpot(List<ParkingFloor> floors, Vehicle vehicle) {
        return floors.stream()
                .flatMap(floor -> floor.getSpots().stream())
                .filter(ParkingSpot::isAvailable)
                .filter(spot -> spot.canFit(vehicle))
                .min(Comparator.comparingInt(ParkingSpot::getFloorNumber)
                        .thenComparingInt(ParkingSpot::getDistanceFromEntrance));
    }
}
