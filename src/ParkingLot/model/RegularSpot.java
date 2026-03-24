package ParkingLot.model;

/**
 * Regular spot — fits motorcycles and cars.
 */
public class RegularSpot extends ParkingSpot {
    public RegularSpot(String spotId, int floorNumber, int distanceFromEntrance) {
        super(spotId, SpotType.REGULAR, floorNumber, distanceFromEntrance);
    }

    @Override
    public boolean canFit(Vehicle vehicle) {
        return vehicle.getType() == VehicleType.MOTORCYCLE ||
               vehicle.getType() == VehicleType.CAR;
    }
}
