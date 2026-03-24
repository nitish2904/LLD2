package ParkingLot.model;

/**
 * Compact spot — fits only motorcycles.
 */
public class CompactSpot extends ParkingSpot {
    public CompactSpot(String spotId, int floorNumber, int distanceFromEntrance) {
        super(spotId, SpotType.COMPACT, floorNumber, distanceFromEntrance);
    }

    @Override
    public boolean canFit(Vehicle vehicle) {
        return vehicle.getType() == VehicleType.MOTORCYCLE;
    }
}
