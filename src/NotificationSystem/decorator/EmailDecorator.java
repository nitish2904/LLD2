package NotificationSystem.decorator;
import NotificationSystem.model.Notifier;
public class EmailDecorator extends NotifierDecorator {
    public EmailDecorator(Notifier wrapped) { super(wrapped); }
    @Override public void send(String userId, String message) {
        super.send(userId, message);
        System.out.println("[EMAIL] 📧 Sent email to " + userId + ": \"" + message + "\"");
    }
}
