package fr.aphp.referential.load.domain.type.ghmghs.f001;

public enum GhmMetadataType {
    BORNE_BASSE,
    BORNE_HAUTE;

    public String representation() {
        return name().toLowerCase();
    }
}

