package ne.ecole.schoolmgmt.entity;

public enum StudentStatus {
    ACTIVE("Actif"),
    SUSPENDED("Suspendu"),
    GRADUATED("Diplômé"),
    TRANSFERRED("Transféré"),
    DROPPED_OUT("Abandonné");

    private final String displayName;

    StudentStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

