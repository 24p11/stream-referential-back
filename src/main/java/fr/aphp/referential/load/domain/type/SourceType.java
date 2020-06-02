package fr.aphp.referential.load.domain.type;

import static java.lang.String.format;
import static java.util.Arrays.stream;

public enum SourceType implements BaseType {
    CIM10("cim10"),
    CCAM("ccam"),
    GHMGHS("ghmghs"),
    GHM("ghm"),
    GHS("ghs"),
    DMI("dmi"),
    MO_REFERENTIAL("mo_referential"),
    MO_INDICATION("mo_indication"),
    LIST("list");

    private final String representation;

    SourceType(String representation) {
        this.representation = representation;
    }

    public static SourceType fromRepresentation(String representation) {
        return stream(values())
                .filter(sourceType -> sourceType.representation.equals(representation))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(format("Invalid sourceType '%s'", representation)));
    }
}
