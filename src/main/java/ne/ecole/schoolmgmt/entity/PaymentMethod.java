package ne.ecole.schoolmgmt.entity;

public enum PaymentMethod {
    ESPECES("Espèces"),
    CHEQUE("Chèque"),
    VIREMENT_BANCAIRE("Virement bancaire"),
    MOBILE_MONEY("Mobile Money"),
    ORANGE_MONEY("Orange Money"),
    AIRTEL_MONEY("Airtel Money"),
    MOOV_MONEY("Moov Money"),
    CARTE_BANCAIRE("Carte bancaire");

    private final String displayName;

    PaymentMethod(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

