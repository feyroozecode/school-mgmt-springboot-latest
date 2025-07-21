package ne.ecole.schoolmgmt.entity;

public enum NotificationStatus {
    PENDING("En attente"),
    SENT("Envoyée"),
    DELIVERED("Livrée"),
    READ("Lue"),
    FAILED("Échec"),
    CANCELLED("Annulée");

    private final String displayName;

    NotificationStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

