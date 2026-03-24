package RideSharing.observer;
import RideSharing.model.Ride;
public class NotificationService implements RideObserver {
    public void onRideRequested(Ride r) { System.out.println("[NOTIF] 📱 Ride requested: " + r.getRider().getName() + " from " + r.getPickup()); }
    public void onRideMatched(Ride r) { System.out.println("[NOTIF] 📱 Driver " + r.getDriver().getName() + " matched for " + r.getRider().getName()); }
    public void onRideCompleted(Ride r) { System.out.println("[NOTIF] 📱 Ride completed | Fare: $" + String.format("%.2f", r.getFare())); }
    public void onRideCancelled(Ride r) { System.out.println("[NOTIF] 📱 Ride cancelled: " + r.getRideId()); }
}
