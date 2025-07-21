package ne.ecole.schoolmgmt.entity;

public enum NotificationType {
    PAYMENT_REMINDER("Rappel de paiement"),
    PAYMENT_CONFIRMATION("Confirmation de paiement"),
    GRADE_PUBLISHED("Note publiée"),
    ABSENCE_ALERT("Alerte d'absence"),
    EXAM_SCHEDULE("Calendrier d'examen"),
    GENERAL_ANNOUNCEMENT("Annonce générale"),
    DISCIPLINARY_ACTION("Action disciplinaire"),
    PARENT_MEETING("Réunion de parents"),
    SCHOOL_CLOSURE("Fermeture d'école"),
    ENROLLMENT_REMINDER("Rappel d'inscription"),
    DOCUMENT_REQUEST("Demande de document"),
    SYSTEM_MAINTENANCE("Maintenance système");

    private final String displayName;

    NotificationType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

