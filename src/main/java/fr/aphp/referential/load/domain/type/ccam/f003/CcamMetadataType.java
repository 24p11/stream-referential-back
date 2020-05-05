package fr.aphp.referential.load.domain.type.ccam.f003;

public enum CcamMetadataType {
    EXTENSION_CODES;

    public String representation() {
        return name().toLowerCase();
    }
}
