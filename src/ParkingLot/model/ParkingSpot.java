package ParkingLot.model;

/**
 * Abstract base class for parking spots.
 * Each subclass defines which vehicle types it can fit (Liskov Substitution Principle).
 */
public abstract class ParkingSpot {
    private final String spotId;
    private final SpotType type;
    private final int floorNumber;
    private final int distanceFromEntrance;
    private Vehicle currentVehicle;
    private boolean available;

    protected ParkingSpot(String spotId, SpotType type, int floorNumber, int distanceFromEntrance) {
        this.spotId = spotId;
        this.type = type;
        this.floorNumber = floorNumber;
        this.distanceFromEntrance = distanceFromEntrance;
        this.available = true;
        this.currentVehicle = null;
    }

    /**
     * Template method — subclasses define fitting rules.
     */
    public abstract boolean canFit(Vehicle vehicle);

    public boolean park(Vehicle vehicle) {
        if (!available || !canFit(vehicle)) {
            return false;
        }
        this.currentVehicle = vehicle;
        this.available = false;
        return true;
    }

    public Vehicle unpark() {
        Vehicle vehicle = this.currentVehicle;
        this.currentVehicle = null;
        this.available = true;
        return vehicle;
    }

    public boolean isAvailable() {
        return available;
    }

    public String getSpotId() {
        return spotId;
    }

    public SpotType getType() {
        return type;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public int getDistanceFromEntrance() {
        return distanceFromEntrance;
    }

    public Vehicle getCurrentVehicle() {
        return currentVehicle;
    }

    @Override
    public String toString() {
        return type + "-" + spotId + "(floor=" + floorNumber + ", dist=" + distanceFromEntrance +
               ", " + (available ? "FREE" : "OCCUPIED by " + currentVehicle) + ")";
    }
}
