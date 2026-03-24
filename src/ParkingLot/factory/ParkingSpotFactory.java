package ParkingLot.factory;

import ParkingLot.model.*;

/**
 * Factory for creating ParkingSpot instances.
 */
public class ParkingSpotFactory {

    public static ParkingSpot create(SpotType type, String spotId, int floorNumber, int distanceFromEntrance) {
        switch (type) {
            case COMPACT: return new CompactSpot(spotId, floorNumber, distanceFromEntrance);
            case REGULAR: return new RegularSpot(spotId, floorNumber, distanceFromEntrance);
            case LARGE:   return new LargeSpot(spotId, floorNumber, distanceFromEntrance);
            default:
                throw new IllegalArgumentException("Unknown spot type: " + type);
        }
    }
}
