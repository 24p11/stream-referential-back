package fr.aphp.referential.load.domain.type.ghmghs.f002;

public enum GhmMetadataType {
    TARIF;

    public String representation() {
        return name().toLowerCase();
    }
}
