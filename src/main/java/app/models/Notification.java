package app.models;

public class Notification {
    private final String id;
    private final String title;
    private final String message;
    private final String date;
    private final String time;
    private final NotificationType type;

    public enum NotificationType {
        ORDER_UPDATE, PAYMENT, DELIVERY, SYSTEM
    }

    public Notification(String id, String title, String message, String date, String time, NotificationType type) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.date = date;
        this.time = time;
        this.type = type;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getMessage() { return message; }
    public String getDate() { return date; }
    public String getTime() { return time; }
    public NotificationType getType() { return type; }
}
