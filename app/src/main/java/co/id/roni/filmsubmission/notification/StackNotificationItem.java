package co.id.roni.filmsubmission.notification;

public class StackNotificationItem {
    private int id;
    private String sender;
    private String message;

    public StackNotificationItem(int id, String sender, String message) {
        this.id = id;
        this.sender = sender;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public String getMessage() {
        return message;
    }
}
