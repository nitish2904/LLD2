package SocialMedia.model;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
public class Post {
    private final String postId, authorId, content;
    private final LocalDateTime createdAt;
    private final AtomicInteger likes = new AtomicInteger(0);
    private final List<String> comments = new CopyOnWriteArrayList<>();
    public Post(String authorId, String content) {
        this.postId = "POST-" + UUID.randomUUID().toString().substring(0,6).toUpperCase();
        this.authorId = authorId; this.content = content; this.createdAt = LocalDateTime.now();
    }
    public void like() { likes.incrementAndGet(); }
    public void addComment(String comment) { comments.add(comment); }
    public String getPostId() { return postId; }
    public String getAuthorId() { return authorId; }
    public String getContent() { return content; }
    public int getLikes() { return likes.get(); }
    public List<String> getComments() { return comments; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    @Override public String toString() { return "Post[" + postId + " by " + authorId + " | \"" + content + "\" | ❤️" + likes.get() + " | 💬" + comments.size() + "]"; }
}
