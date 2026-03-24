package RideSharing.observer;
import RideSharing.model.Ride;
public interface RideObserver {
    void onRideRequested(Ride ride);
    void onRideMatched(Ride ride);
    void onRideCompleted(Ride ride);
    void onRideCancelled(Ride ride);
}
