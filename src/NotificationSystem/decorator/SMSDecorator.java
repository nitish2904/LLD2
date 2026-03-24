package NotificationSystem.decorator;
import NotificationSystem.model.Notifier;
public class SMSDecorator extends NotifierDecorator {
    public SMSDecorator(Notifier wrapped) { super(wrapped); }
    @Override public void send(String userId, String message) {
        super.send(userId, message);
        System.out.println("[SMS] 📱 Sent SMS to " + userId + ": \"" + message + "\"");
    }
}
