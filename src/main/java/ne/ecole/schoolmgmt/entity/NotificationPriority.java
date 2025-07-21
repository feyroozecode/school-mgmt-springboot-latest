package ne.ecole.schoolmgmt.entity;

public enum NotificationPriority {
    LOW("Faible"),
    NORMAL("Normale"),
    HIGH("Élevée"),
    URGENT("Urgente");

    private final String displayName;

    NotificationPriority(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

