package fr.aphp.referential.load.domain.type.ghmghs.f001;

public enum GhsMetadataType {
    EXB,
    EXB_FORFAIT,
    EXH;

    public String representation() {
        return name().toLowerCase();
    }
}
