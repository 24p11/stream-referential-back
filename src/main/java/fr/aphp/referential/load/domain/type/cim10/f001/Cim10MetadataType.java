package fr.aphp.referential.load.domain.type.cim10.f001;

public enum Cim10MetadataType {
    MCO_HAD,
    SSR,
    PSY;

    public String representation() {
        return name().toLowerCase();
    }
}
