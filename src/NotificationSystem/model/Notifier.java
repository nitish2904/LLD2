package NotificationSystem.model;
public interface Notifier {
    void send(String userId, String message);
}
