package ChatApplication.observer;
import ChatApplication.model.Message;
public interface ChatObserver { void onMessage(String chatName, String recipientId, Message msg); }
