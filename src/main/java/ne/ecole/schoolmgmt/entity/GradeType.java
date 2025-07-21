package ne.ecole.schoolmgmt.entity;

public enum GradeType {
    DEVOIR("Devoir"),
    INTERROGATION("Interrogation"),
    COMPOSITION("Composition"),
    EXAMEN_BLANC("Examen blanc"),
    EXAMEN_FINAL("Examen final"),
    CONTROLE_CONTINU("Contrôle continu"),
    PROJET("Projet"),
    EXPOSE("Exposé"),
    TRAVAUX_PRATIQUES("Travaux pratiques");

    private final String displayName;

    GradeType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

