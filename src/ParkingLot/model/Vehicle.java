package ParkingLot.model;

/**
 * Abstract base class for all vehicles.
 * Follows Open/Closed Principle — new vehicle types extend this without modifying it.
 */
public abstract class Vehicle {
    private final String licensePlate;
    private final VehicleType type;

    protected Vehicle(String licensePlate, VehicleType type) {
        this.licensePlate = licensePlate;
        this.type = type;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public VehicleType getType() {
        return type;
    }

    @Override
    public String toString() {
        return type + "[" + licensePlate + "]";
    }
}
