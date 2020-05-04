package fr.aphp.referential.load.domain.type.cim10.f001;

public enum Cim10MetadataType {
    MCO_HAD("mco_had"),
    SSR("ssr"),
    PSY("psy");

    private final String representation;

    Cim10MetadataType(String representation) {
        this.representation = representation;
    }

    public String representation() {
        return representation;
    }
}
