package fr.aphp.referential.load.domain.type.list.f002;

public enum ListMetadataType {
    LIST,
    NAME,
    VOCABULARY,
    VERSION,
    AUTHOR,
    START_DATE,
    END_DATE,
    CODE,
    CODES;

    public String representation() {
        return name().toLowerCase();
    }
}
