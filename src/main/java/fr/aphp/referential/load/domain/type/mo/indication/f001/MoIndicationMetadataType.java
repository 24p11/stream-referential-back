package fr.aphp.referential.load.domain.type.mo.indication.f001;

public enum MoIndicationMetadataType {
    INSCRIPTION,
    DCI,
    LABO,
    LIB_INDICATION,
    CLASS_IND_1,
    CLASS_IND_2,
    GENER;

    public String representation() {
        return name().toLowerCase();
    }
}
