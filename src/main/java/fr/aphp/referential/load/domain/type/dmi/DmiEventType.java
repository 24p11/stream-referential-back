package fr.aphp.referential.load.domain.type.dmi;

import static java.util.Arrays.stream;

public enum DmiEventType {
    REGISTER("inscription"),
    DELETE("suppression");

    private final String representation;

    DmiEventType(String representation) {
        this.representation = representation;
    }

    public static DmiEventType fromRepresentation(String representation) {
        return stream(values())
                .filter(dmiEventType -> dmiEventType.representation.equals(representation))
                .findFirst()
                // By default consider as creation ?
                .orElse(REGISTER);
    }
}
