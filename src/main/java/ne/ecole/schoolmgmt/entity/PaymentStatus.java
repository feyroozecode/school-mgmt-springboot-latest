package ne.ecole.schoolmgmt.entity;

public enum PaymentStatus {
    PENDING("En attente"),
    COMPLETED("Terminé"),
    CANCELLED("Annulé"),
    REFUNDED("Remboursé"),
    PARTIAL("Partiel");

    private final String displayName;

    PaymentStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

