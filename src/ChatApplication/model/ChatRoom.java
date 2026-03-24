package ChatApplication.model;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
public class ChatRoom {
    private final String roomId, name;
    private final boolean isGroup;
    private final Set<String> members = ConcurrentHashMap.newKeySet();
    private final List<Message> history = new CopyOnWriteArrayList<>();
    public ChatRoom(String roomId, String name, boolean isGroup) { this.roomId = roomId; this.name = name; this.isGroup = isGroup; }
    public void addMember(String userId) { members.add(userId); }
    public void removeMember(String userId) { members.remove(userId); }
    public void addMessage(Message msg) { history.add(msg); }
    public String getRoomId() { return roomId; }
    public String getName() { return name; }
    public boolean isGroup() { return isGroup; }
    public Set<String> getMembers() { return members; }
    public List<Message> getHistory() { return history; }
    @Override public String toString() { return (isGroup ? "Group" : "DM") + "[" + name + " | " + members.size() + " members | " + history.size() + " msgs]"; }
}
