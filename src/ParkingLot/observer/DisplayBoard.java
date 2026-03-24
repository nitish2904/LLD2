package ParkingLot.observer;

import ParkingLot.model.ParkingFloor;
import ParkingLot.model.ParkingSpot;

/**
 * Displays real-time notifications when spots change.
 */
public class DisplayBoard implements ParkingLotObserver {

    @Override
    public void onSpotTaken(ParkingFloor floor, ParkingSpot spot) {
        System.out.println("[DISPLAY] 🔴 Spot " + spot.getSpotId() + " on " + floor +
                " is now OCCUPIED by " + spot.getCurrentVehicle());
    }

    @Override
    public void onSpotFreed(ParkingFloor floor, ParkingSpot spot) {
        System.out.println("[DISPLAY] 🟢 Spot " + spot.getSpotId() + " on " + floor +
                " is now FREE");
    }
}
