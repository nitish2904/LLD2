package ParkingLot.model;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Immutable entry ticket issued when a vehicle enters the parking lot.
 */
public class Ticket {
    private final String ticketId;
    private final Vehicle vehicle;
    private final ParkingSpot spot;
    private final LocalDateTime entryTime;
    private LocalDateTime exitTime;
    private boolean active;

    public Ticket(Vehicle vehicle, ParkingSpot spot) {
        this.ticketId = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        this.vehicle = vehicle;
        this.spot = spot;
        this.entryTime = LocalDateTime.now();
        this.active = true;
    }

    public void close() {
        this.exitTime = LocalDateTime.now();
        this.active = false;
    }

    public String getTicketId() { return ticketId; }
    public Vehicle getVehicle() { return vehicle; }
    public ParkingSpot getSpot() { return spot; }
    public LocalDateTime getEntryTime() { return entryTime; }
    public LocalDateTime getExitTime() { return exitTime; }
    public boolean isActive() { return active; }

    @Override
    public String toString() {
        return "Ticket[" + ticketId + " | " + vehicle + " → " + spot.getSpotId() +
               " | entry=" + entryTime + (active ? " | ACTIVE" : " | exit=" + exitTime) + "]";
    }
}
