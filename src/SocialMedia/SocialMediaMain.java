package SocialMedia;
import SocialMedia.model.*;
import SocialMedia.observer.NotificationService;
import SocialMedia.service.SocialMediaService;
public class SocialMediaMain {
    public static void main(String[] args) {
        System.out.println("╔════════════════════════════════════╗");
        System.out.println("║   📱 SOCIAL MEDIA SYSTEM DEMO     ║");
        System.out.println("╚════════════════════════════════════╝\n");
        SocialMediaService sm = new SocialMediaService();
        sm.addObserver(new NotificationService());
        sm.register("alice", "Alice"); sm.register("bob", "Bob"); sm.register("charlie", "Charlie");

        System.out.println("\n═══ SCENARIO 1: Follow & Post ═══\n");
        sm.follow("bob", "alice"); sm.follow("charlie", "alice");
        Post p1 = sm.createPost("alice", "Hello World! My first post 🌍");

        System.out.println("\n═══ SCENARIO 2: Like & Comment ═══\n");
        sm.likePost("bob", p1); sm.likePost("charlie", p1);
        sm.commentOnPost("bob", p1, "Great post!");
        sm.commentOnPost("charlie", p1, "Welcome to the platform!");
        System.out.println("Post after interactions: " + p1);

        System.out.println("\n═══ SCENARIO 3: Multiple posts & Feed ═══\n");
        sm.follow("alice", "bob");
        Post p2 = sm.createPost("bob", "Design patterns are awesome! 💡");
        Post p3 = sm.createPost("alice", "Second post from me!");
        System.out.println("\nBob's feed:");
        sm.getFeed("bob").forEach(p -> System.out.println("  " + p));
        System.out.println("\nAlice's feed:");
        sm.getFeed("alice").forEach(p -> System.out.println("  " + p));

        System.out.println("\n═══ SCENARIO 4: Unfollow ═══\n");
        sm.unfollow("bob", "alice");
        Post p4 = sm.createPost("alice", "Bob won't see this!");
        System.out.println("\nBob's feed after unfollow:");
        sm.getFeed("bob").forEach(p -> System.out.println("  " + p));

        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║   ✅ ALL SCENARIOS COMPLETE         ║");
        System.out.println("╚════════════════════════════════════╝");
    }
}
