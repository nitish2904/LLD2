package ChatApplication.model;
import java.time.LocalDateTime;
import java.util.UUID;
public class Message {
    private final String messageId, senderId, content;
    private final LocalDateTime timestamp;
    public Message(String senderId, String content) {
        this.messageId = "MSG-" + UUID.randomUUID().toString().substring(0,6).toUpperCase();
        this.senderId = senderId; this.content = content; this.timestamp = LocalDateTime.now();
    }
    public String getMessageId() { return messageId; }
    public String getSenderId() { return senderId; }
    public String getContent() { return content; }
    @Override public String toString() { return "[" + senderId + "]: " + content; }
}
