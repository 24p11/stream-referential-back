package fr.aphp.referential.load.domain.type.list.f003;

public enum ListMetadataType {
    LIST,
    NAME,
    VOCABULARY,
    VERSION,
    AUTHOR,
    START_DATE,
    END_DATE,
    CODE,
    DEVICE,
    ORGAN;

    public String representation() {
        return name().toLowerCase();
    }
}
