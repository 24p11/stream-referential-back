package fr.aphp.referential.load.domain.type.mo;

import static java.util.Arrays.stream;

public enum MoEventType {
    REGISTER("inscription"),
    MODIFY("modification"),
    DELETE("suppression");

    private final String representation;

    MoEventType(String representation) {
        this.representation = representation;
    }

    public static MoEventType fromIdentifier(String representation) {
        return stream(values())
                .filter(moEventType -> moEventType.representation.equals(representation))
                .findFirst()
                // By default consider as creation ?
                .orElse(REGISTER);
    }
}
