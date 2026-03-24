package MovieTicketBooking.model;
public class Seat {
    private final String seatId;
    private final int row;
    private final int col;
    private final SeatType type;
    private final double price;
    private volatile SeatStatus status;

    public Seat(String seatId, int row, int col, SeatType type, double price) {
        this.seatId = seatId; this.row = row; this.col = col;
        this.type = type; this.price = price; this.status = SeatStatus.AVAILABLE;
    }
    public synchronized boolean hold() {
        if (status != SeatStatus.AVAILABLE) return false;
        status = SeatStatus.HELD; return true;
    }
    public synchronized boolean book() {
        if (status != SeatStatus.HELD) return false;
        status = SeatStatus.BOOKED; return true;
    }
    public synchronized void release() { status = SeatStatus.AVAILABLE; }
    public synchronized void cancel() {
        if (status == SeatStatus.BOOKED) status = SeatStatus.AVAILABLE;
    }
    public String getSeatId() { return seatId; }
    public int getRow() { return row; }
    public int getCol() { return col; }
    public SeatType getType() { return type; }
    public double getPrice() { return price; }
    public SeatStatus getStatus() { return status; }
    public boolean isAvailable() { return status == SeatStatus.AVAILABLE; }
    @Override public String toString() { return seatId + "(" + type + ",$" + price + "," + status + ")"; }
}
