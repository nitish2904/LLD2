package ChatApplication.service;
import ChatApplication.model.*;
import ChatApplication.observer.ChatObserver;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
public class ChatService {
    private final Map<String, User> users = new LinkedHashMap<>();
    private final Map<String, ChatRoom> rooms = new LinkedHashMap<>();
    private final List<ChatObserver> observers = new CopyOnWriteArrayList<>();
    public void addObserver(ChatObserver o) { observers.add(o); }
    public User register(String userId, String name) { User u = new User(userId, name); users.put(userId, u); System.out.println("[CHAT] Registered: " + u); return u; }
    public ChatRoom createGroup(String roomId, String name, String... memberIds) {
        ChatRoom room = new ChatRoom(roomId, name, true);
        for (String id : memberIds) room.addMember(id);
        rooms.put(roomId, room);
        System.out.println("[CHAT] Created: " + room); return room;
    }
    public ChatRoom getOrCreateDM(String user1, String user2) {
        String dmId = user1.compareTo(user2) < 0 ? "dm:" + user1 + ":" + user2 : "dm:" + user2 + ":" + user1;
        return rooms.computeIfAbsent(dmId, id -> { ChatRoom r = new ChatRoom(id, user1 + "↔" + user2, false); r.addMember(user1); r.addMember(user2); return r; });
    }
    public void sendMessage(String senderId, String roomId, String content) {
        ChatRoom room = rooms.get(roomId);
        if (room == null) { System.out.println("[CHAT] ❌ Room not found"); return; }
        Message msg = new Message(senderId, content);
        room.addMessage(msg);
        room.getMembers().stream().filter(id -> !id.equals(senderId)).forEach(id -> {
            User recipient = users.get(id);
            if (recipient != null && recipient.isOnline()) recipient.receiveMessage(room.getName(), msg);
            else System.out.println("[CHAT] 📭 " + id + " is offline, message queued");
            observers.forEach(o -> o.onMessage(room.getName(), id, msg));
        });
    }
    public void sendDM(String fromId, String toId, String content) {
        ChatRoom dm = getOrCreateDM(fromId, toId);
        sendMessage(fromId, dm.getRoomId(), content);
    }
    public void showHistory(String roomId) {
        ChatRoom room = rooms.get(roomId);
        System.out.println("History of " + room + ":"); room.getHistory().forEach(m -> System.out.println("  " + m));
    }
}
