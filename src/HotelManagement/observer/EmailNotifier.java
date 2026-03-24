package HotelManagement.observer;
import HotelManagement.model.Reservation;
public class EmailNotifier implements HotelObserver {
    public void onReservation(Reservation r) { System.out.println("[EMAIL] 📧 Reservation confirmed: " + r.getReservationId() + " for " + r.getGuest().getName()); }
    public void onCheckIn(Reservation r) { System.out.println("[EMAIL] 📧 Checked in: " + r.getGuest().getName() + " → Room " + r.getRoom().getRoomNumber()); }
    public void onCheckOut(Reservation r) { System.out.println("[EMAIL] 📧 Checked out: " + r.getGuest().getName() + " | Total: $" + String.format("%.2f", r.getTotalAmount())); }
    public void onCancellation(Reservation r) { System.out.println("[EMAIL] 📧 Cancelled: " + r.getReservationId()); }
}
