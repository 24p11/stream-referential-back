package fr.aphp.referential.load.domain.type.ccam.f001;

public enum CcamMetadataType {
    EXTENSION_PMSI,
    CODE_PMSI,
    HAS,
    CONSIGN_PMSI,
    MODIFICATION_TYPE,
    MODIFICATION_VERSION,
    RSC,
    AP,
    ETM,
    RGT,
    CLASSIFYING,
    BILLING_LIST,
    ICR,
    ICR_PRIVATE,
    ICR_A4,
    ICR_ANAPATH,
    ICR_REA,
    MODIFIER,
    GEST_COMP,
    GEST_COMP_ANES,
    DENOM;

    public String representation() {
        return name().toLowerCase();
    }
}
