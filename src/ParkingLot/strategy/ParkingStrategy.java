package ParkingLot.strategy;

import ParkingLot.model.ParkingFloor;
import ParkingLot.model.ParkingSpot;
import ParkingLot.model.Vehicle;

import java.util.List;
import java.util.Optional;

/**
 * Strategy interface for spot selection algorithms.
 * Follows Interface Segregation — single focused method.
 * Follows Dependency Inversion — ParkingService depends on this abstraction.
 */
public interface ParkingStrategy {
    Optional<ParkingSpot> findSpot(List<ParkingFloor> floors, Vehicle vehicle);
}
