package fr.aphp.referential.load.domain.type.cim10;

public enum Cim10F001MetadataType {
    MCO_HAD("mco_had"),
    SSR("ssr"),
    PSY("psy");

    private final String representation;

    Cim10F001MetadataType(String representation) {
        this.representation = representation;
    }

    public String representation() {
        return representation;
    }
}
