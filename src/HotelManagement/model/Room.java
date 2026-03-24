package HotelManagement.model;
public class Room {
    private final String roomNumber;
    private final RoomType type;
    private final double pricePerNight;
    private final int floor;
    private volatile RoomStatus status;
    public Room(String roomNumber, RoomType type, double pricePerNight, int floor) {
        this.roomNumber = roomNumber; this.type = type; this.pricePerNight = pricePerNight; this.floor = floor; this.status = RoomStatus.AVAILABLE;
    }
    public synchronized boolean book() { if (status != RoomStatus.AVAILABLE) return false; status = RoomStatus.BOOKED; return true; }
    public synchronized void checkIn() { if (status == RoomStatus.BOOKED) status = RoomStatus.OCCUPIED; }
    public synchronized void checkOut() { status = RoomStatus.AVAILABLE; }
    public synchronized void setMaintenance() { status = RoomStatus.MAINTENANCE; }
    public String getRoomNumber() { return roomNumber; }
    public RoomType getType() { return type; }
    public double getPricePerNight() { return pricePerNight; }
    public int getFloor() { return floor; }
    public RoomStatus getStatus() { return status; }
    public boolean isAvailable() { return status == RoomStatus.AVAILABLE; }
    @Override public String toString() { return "Room[" + roomNumber + " | " + type + " | $" + pricePerNight + "/night | " + status + "]"; }
}
