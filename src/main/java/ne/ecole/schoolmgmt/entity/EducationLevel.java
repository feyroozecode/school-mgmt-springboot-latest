package ne.ecole.schoolmgmt.entity;

public enum EducationLevel {
    // Enseignement Primaire
    CI("Cours d'Initiation"),
    CP("Cours Préparatoire"),
    CE1("Cours Élémentaire 1ère année"),
    CE2("Cours Élémentaire 2ème année"),
    CM1("Cours Moyen 1ère année"),
    CM2("Cours Moyen 2ème année"),

    // Enseignement Secondaire Premier Cycle
    SIXIEME("6ème"),
    CINQUIEME("5ème"),
    QUATRIEME("4ème"),
    TROISIEME("3ème"),

    // Enseignement Secondaire Second Cycle
    SECONDE("2nde"),
    PREMIERE("1ère"),
    TERMINALE("Terminale"),

    // Enseignement Technique et Professionnel
    CAP1("CAP 1ère année"),
    CAP2("CAP 2ème année"),
    BEP1("BEP 1ère année"),
    BEP2("BEP 2ème année"),

    // Enseignement Supérieur
    LICENCE1("Licence 1"),
    LICENCE2("Licence 2"),
    LICENCE3("Licence 3"),
    MASTER1("Master 1"),
    MASTER2("Master 2");

    private final String displayName;

    EducationLevel(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public boolean isPrimary() {
        return this.ordinal() <= CM2.ordinal();
    }

    public boolean isSecondary() {
        return this.ordinal() >= SIXIEME.ordinal() && this.ordinal() <= TERMINALE.ordinal();
    }

    public boolean isTechnical() {
        return this.ordinal() >= CAP1.ordinal() && this.ordinal() <= BEP2.ordinal();
    }

    public boolean isHigherEducation() {
        return this.ordinal() >= LICENCE1.ordinal();
    }
}

