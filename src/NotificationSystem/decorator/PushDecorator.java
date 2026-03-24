package NotificationSystem.decorator;
import NotificationSystem.model.Notifier;
public class PushDecorator extends NotifierDecorator {
    public PushDecorator(Notifier wrapped) { super(wrapped); }
    @Override public void send(String userId, String message) {
        super.send(userId, message);
        System.out.println("[PUSH] 🔔 Sent push to " + userId + ": \"" + message + "\"");
    }
}
