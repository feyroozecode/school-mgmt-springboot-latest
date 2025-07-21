package ne.ecole.schoolmgmt.entity;

public enum NotificationChannel {
    SMS("SMS"),
    EMAIL("Email"),
    IN_APP("Dans l'application"),
    PUSH("Notification push"),
    WHATSAPP("WhatsApp");

    private final String displayName;

    NotificationChannel(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

