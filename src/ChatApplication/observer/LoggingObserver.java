package ChatApplication.observer;
import ChatApplication.model.Message;
public class LoggingObserver implements ChatObserver {
    public void onMessage(String chatName, String recipientId, Message msg) { System.out.println("[LOG] " + chatName + " → " + recipientId + " : " + msg.getContent()); }
}
