package fr.aphp.referential.load.domain.type.ccam.f002;

public enum CcamMetadataType {
    PHASE,
    ACTIVITY,
    EXTENSION;

    public String representation() {
        return name().toLowerCase();
    }
}
