package fr.aphp.referential.load.domain.type.mo.referential;

import static java.util.Arrays.stream;

public enum MoReferentialEventType {
    REGISTER("inscription"),
    MODIFY("modification"),
    DELETE("radiation");

    private final String representation;

    MoReferentialEventType(String representation) {
        this.representation = representation;
    }

    public static MoReferentialEventType fromIdentifier(String representation) {
        return stream(values())
                .filter(moReferentialEventType -> moReferentialEventType.representation.equals(representation))
                .findFirst()
                // By default consider as creation ?
                .orElse(REGISTER);
    }
}
