package SocialMedia.observer;
import SocialMedia.model.Post;
public class NotificationService implements FeedObserver {
    public void onNewPost(String followerId, Post post) { System.out.println("[NOTIF] 📱 " + followerId + " → new post from " + post.getAuthorId() + ": \"" + post.getContent() + "\""); }
    public void onLike(String userId, Post post) { System.out.println("[NOTIF] ❤️ " + userId + " liked " + post.getPostId()); }
}
