package ParkingLot.observer;

import ParkingLot.model.ParkingFloor;
import ParkingLot.model.ParkingSpot;

/**
 * Observer interface for parking lot state changes.
 * Follows Interface Segregation — focused on spot availability events.
 */
public interface ParkingLotObserver {
    void onSpotTaken(ParkingFloor floor, ParkingSpot spot);
    void onSpotFreed(ParkingFloor floor, ParkingSpot spot);
}
