package SocialMedia.service;
import SocialMedia.model.*;
import SocialMedia.observer.FeedObserver;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
public class SocialMediaService {
    private final Map<String, User> users = new LinkedHashMap<>();
    private final List<FeedObserver> observers = new CopyOnWriteArrayList<>();
    public void addObserver(FeedObserver o) { observers.add(o); }
    public User register(String userId, String name) { User u = new User(userId, name); users.put(userId, u); System.out.println("[SM] Registered: " + u); return u; }
    public void follow(String followerId, String followeeId) {
        User follower = users.get(followerId), followee = users.get(followeeId);
        if (follower != null && followee != null) { follower.follow(followee); System.out.println("[SM] " + follower.getName() + " → follows → " + followee.getName()); }
    }
    public void unfollow(String followerId, String followeeId) {
        User follower = users.get(followerId), followee = users.get(followeeId);
        if (follower != null && followee != null) { follower.unfollow(followee); System.out.println("[SM] " + follower.getName() + " unfollowed " + followee.getName()); }
    }
    public Post createPost(String userId, String content) {
        User user = users.get(userId);
        Post post = new Post(userId, content); user.addPost(post);
        System.out.println("[SM] ✅ " + post);
        user.getFollowers().forEach(fid -> observers.forEach(o -> o.onNewPost(fid, post)));
        return post;
    }
    public void likePost(String userId, Post post) { post.like(); observers.forEach(o -> o.onLike(userId, post)); }
    public void commentOnPost(String userId, Post post, String comment) { post.addComment(userId + ": " + comment); System.out.println("[SM] 💬 " + userId + " commented on " + post.getPostId() + ": \"" + comment + "\""); }
    public List<Post> getFeed(String userId) {
        User user = users.get(userId);
        return user.getFollowing().stream().map(users::get).filter(Objects::nonNull).flatMap(u -> u.getPosts().stream()).sorted(Comparator.comparing(Post::getCreatedAt).reversed()).collect(Collectors.toList());
    }
}
