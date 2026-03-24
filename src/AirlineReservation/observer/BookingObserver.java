package AirlineReservation.observer;
import AirlineReservation.model.Booking;
public interface BookingObserver { void onBooking(Booking b); void onCancellation(Booking b); }
