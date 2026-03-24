package NotificationSystem.model;
public class BaseNotifier implements Notifier {
    public void send(String userId, String message) {
        System.out.println("[BASE] 📋 Logged notification for " + userId + ": \"" + message + "\"");
    }
}
