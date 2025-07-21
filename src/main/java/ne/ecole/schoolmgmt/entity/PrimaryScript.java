package ne.ecole.schoolmgmt.entity;

public enum PrimaryScript {
    LATIN("Latin"),
    ARABIC("Arabe"),
    BOTH("Bilingue");

    private final String displayName;

    PrimaryScript(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

