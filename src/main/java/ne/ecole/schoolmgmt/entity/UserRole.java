package ne.ecole.schoolmgmt.entity;

public enum UserRole {
    ADMIN("Administrateur"),
    FINANCES("Responsable Finances"),
    PROFESSEUR("Professeur"),
    SECRETARIAT("Secrétariat");

    private final String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

