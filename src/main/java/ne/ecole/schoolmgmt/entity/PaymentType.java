package ne.ecole.schoolmgmt.entity;

public enum PaymentType {
    FRAIS_SCOLARITE("Frais de scolarité"),
    FRAIS_INSCRIPTION("Frais d'inscription"),
    FRAIS_EXAMEN("Frais d'examen"),
    FRAIS_TRANSPORT("Frais de transport"),
    FRAIS_CANTINE("Frais de cantine"),
    FRAIS_UNIFORME("Frais d'uniforme"),
    FRAIS_FOURNITURES("Frais de fournitures"),
    FRAIS_ACTIVITES("Frais d'activités"),
    FRAIS_BIBLIOTHEQUE("Frais de bibliothèque"),
    FRAIS_LABORATOIRE("Frais de laboratoire"),
    FRAIS_INFORMATIQUE("Frais d'informatique"),
    PENALITE_RETARD("Pénalité de retard"),
    AUTRES("Autres");

    private final String displayName;

    PaymentType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

