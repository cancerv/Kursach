import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {
    private final String type;
    private final String from;
    private final String message;
    private final LocalDateTime sentAt;

    public Message(String type, String from, String message) {
        this.type = type;
        this.from = from;
        this.message = message;
        this.sentAt = LocalDateTime.now();
    }

    public String getType() {
        return type;
    }

    public String getFrom() {
        return from;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getSentAt() {
        return sentAt;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");

        return switch (this.type) {
            case "message" -> this.from + " [" + this.sentAt.format(formatter) + "]: " + this.message;
            case "register" -> " ===> " + this.from + " [" + this.sentAt.format(formatter) + "]: присоединился к чату <===";
            default -> this.message;
        };
    }
}
