package SocialMedia.model;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
public class User {
    private final String userId, name;
    private final Set<String> following = ConcurrentHashMap.newKeySet();
    private final Set<String> followers = ConcurrentHashMap.newKeySet();
    private final List<Post> posts = new ArrayList<>();
    public User(String userId, String name) { this.userId = userId; this.name = name; }
    public void follow(User other) { following.add(other.getUserId()); other.followers.add(this.userId); }
    public void unfollow(User other) { following.remove(other.getUserId()); other.followers.remove(this.userId); }
    public void addPost(Post p) { posts.add(p); }
    public String getUserId() { return userId; }
    public String getName() { return name; }
    public Set<String> getFollowing() { return following; }
    public Set<String> getFollowers() { return followers; }
    public List<Post> getPosts() { return posts; }
    @Override public String toString() { return name + "(" + userId + ", " + followers.size() + " followers, " + following.size() + " following)"; }
}
