package ParkingLot.factory;

import ParkingLot.model.*;

/**
 * Factory for creating Vehicle instances.
 * Follows Open/Closed — add new types by adding enum + subclass, no factory modification needed.
 */
public class VehicleFactory {

    public static Vehicle create(VehicleType type, String licensePlate) {
        switch (type) {
            case MOTORCYCLE: return new Motorcycle(licensePlate);
            case CAR:        return new Car(licensePlate);
            case TRUCK:      return new Truck(licensePlate);
            default:
                throw new IllegalArgumentException("Unknown vehicle type: " + type);
        }
    }
}
