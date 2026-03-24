package NotificationSystem.service;
import NotificationSystem.model.Notifier;
public class NotificationService {
    private final Notifier notifier;
    public NotificationService(Notifier notifier) { this.notifier = notifier; }
    public void notify(String userId, String message) {
        System.out.println("--- Sending notification ---");
        notifier.send(userId, message);
        System.out.println();
    }
}
