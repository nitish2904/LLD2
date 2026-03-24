package SocialMedia.observer;
import SocialMedia.model.Post;
public interface FeedObserver {
    void onNewPost(String followerId, Post post);
    void onLike(String userId, Post post);
}
