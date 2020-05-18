package fr.aphp.referential.load.domain.type.mo.referential.f001;

public enum MoReferentialMetadataType {
    LISTE_EN_SUS,
    OUI,
    PRICE,
    PRICE_TTC,
    DCI,
    ATC;

    public String representation() {
        return name().toLowerCase();
    }
}
