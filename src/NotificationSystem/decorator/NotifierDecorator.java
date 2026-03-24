package NotificationSystem.decorator;
import NotificationSystem.model.Notifier;
public abstract class NotifierDecorator implements Notifier {
    protected final Notifier wrapped;
    public NotifierDecorator(Notifier wrapped) { this.wrapped = wrapped; }
    public void send(String userId, String message) { wrapped.send(userId, message); }
}
