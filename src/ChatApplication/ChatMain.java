package ChatApplication;
import ChatApplication.observer.LoggingObserver;
import ChatApplication.service.ChatService;
import ChatApplication.model.*;
public class ChatMain {
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║   💬 CHAT APPLICATION DEMO         ║");
        System.out.println("╚════════════════════════════════════╝\n");
        ChatService chat = new ChatService();
        chat.addObserver(new LoggingObserver());
        chat.register("alice", "Alice"); chat.register("bob", "Bob"); chat.register("charlie", "Charlie");

        System.out.println("\n═══ SCENARIO 1: Group Chat ═══\n");
        ChatRoom group = chat.createGroup("g1", "LLD Study Group", "alice", "bob", "charlie");
        chat.sendMessage("alice", "g1", "Hey everyone! Ready for LLD?");
        chat.sendMessage("bob", "g1", "Let's go! 🚀");

        System.out.println("\n═══ SCENARIO 2: Private DM ═══\n");
        chat.sendDM("alice", "bob", "Hey Bob, great answer in the group!");
        chat.sendDM("bob", "alice", "Thanks Alice! 😄");

        System.out.println("\n═══ SCENARIO 3: Offline user ═══\n");
        chat.register("diana", "Diana").goOffline();
        chat.createGroup("g2", "Project Team", "alice", "diana");
        chat.sendMessage("alice", "g2", "Diana, are you there?");

        System.out.println("\n═══ SCENARIO 4: Chat history ═══\n");
        chat.showHistory("g1");

        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║   ✅ ALL SCENARIOS COMPLETE         ║");
        System.out.println("╚════════════════════════════════════╝");
    }
}
