package ChatApplication.model;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
public class User {
    private final String userId, name;
    private volatile boolean online;
    private final List<String> inbox = new CopyOnWriteArrayList<>();
    public User(String userId, String name) { this.userId = userId; this.name = name; this.online = true; }
    public void receiveMessage(String chatName, Message msg) { String formatted = "(" + chatName + ") " + msg; inbox.add(formatted); System.out.println("[" + name + " INBOX] " + formatted); }
    public void goOffline() { online = false; }
    public void goOnline() { online = true; }
    public String getUserId() { return userId; }
    public String getName() { return name; }
    public boolean isOnline() { return online; }
    public List<String> getInbox() { return inbox; }
    @Override public String toString() { return name + (online ? "🟢" : "⚪"); }
}
