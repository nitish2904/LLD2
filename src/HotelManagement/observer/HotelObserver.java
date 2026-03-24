package HotelManagement.observer;
import HotelManagement.model.Reservation;
public interface HotelObserver {
    void onReservation(Reservation r);
    void onCheckIn(Reservation r);
    void onCheckOut(Reservation r);
    void onCancellation(Reservation r);
}
