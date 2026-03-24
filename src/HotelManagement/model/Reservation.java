package HotelManagement.model;
import java.time.LocalDate;
import java.util.UUID;
public class Reservation {
    public enum Status { ACTIVE, CHECKED_IN, CHECKED_OUT, CANCELLED }
    private final String reservationId;
    private final Guest guest;
    private final Room room;
    private final LocalDate checkIn, checkOut;
    private final double totalAmount;
    private Status status;
    public Reservation(Guest guest, Room room, LocalDate checkIn, LocalDate checkOut) {
        this.reservationId = "RES-" + UUID.randomUUID().toString().substring(0,8).toUpperCase();
        this.guest = guest; this.room = room; this.checkIn = checkIn; this.checkOut = checkOut;
        long nights = checkOut.toEpochDay() - checkIn.toEpochDay();
        this.totalAmount = room.getPricePerNight() * nights; this.status = Status.ACTIVE;
    }
    public void checkIn() { status = Status.CHECKED_IN; room.checkIn(); }
    public void checkOut() { status = Status.CHECKED_OUT; room.checkOut(); }
    public void cancel() { status = Status.CANCELLED; room.checkOut(); }
    public String getReservationId() { return reservationId; }
    public Guest getGuest() { return guest; }
    public Room getRoom() { return room; }
    public double getTotalAmount() { return totalAmount; }
    public Status getStatus() { return status; }
    @Override public String toString() { return "Reservation[" + reservationId + " | " + guest.getName() + " | " + room.getRoomNumber() + " | " + checkIn + " to " + checkOut + " | $" + String.format("%.2f", totalAmount) + " | " + status + "]"; }
}
