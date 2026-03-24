package AirlineReservation.model;
public class Seat {
    private final String seatId; private final SeatType type; private final SeatClass seatClass; private final double price;
    private volatile boolean booked;
    public Seat(String seatId, SeatType type, SeatClass seatClass, double price) { this.seatId = seatId; this.type = type; this.seatClass = seatClass; this.price = price; }
    public synchronized boolean book() { if (booked) return false; booked = true; return true; }
    public synchronized void release() { booked = false; }
    public String getSeatId() { return seatId; }
    public SeatType getType() { return type; }
    public SeatClass getSeatClass() { return seatClass; }
    public double getPrice() { return price; }
    public boolean isBooked() { return booked; }
    @Override public String toString() { return seatId + "(" + seatClass + "/" + type + ",$" + String.format("%.0f", price) + "," + (booked?"BOOKED":"OPEN") + ")"; }
}
