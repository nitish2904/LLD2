package NotificationSystem.decorator;
import NotificationSystem.model.Notifier;
public class SlackDecorator extends NotifierDecorator {
    public SlackDecorator(Notifier wrapped) { super(wrapped); }
    @Override public void send(String userId, String message) {
        super.send(userId, message);
        System.out.println("[SLACK] 💬 Sent Slack to " + userId + ": \"" + message + "\"");
    }
}
