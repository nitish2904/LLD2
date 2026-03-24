package ParkingLot.model;

/**
 * Large spot — fits all vehicle types including trucks.
 */
public class LargeSpot extends ParkingSpot {
    public LargeSpot(String spotId, int floorNumber, int distanceFromEntrance) {
        super(spotId, SpotType.LARGE, floorNumber, distanceFromEntrance);
    }

    @Override
    public boolean canFit(Vehicle vehicle) {
        return true; // Large spots fit any vehicle
    }
}
