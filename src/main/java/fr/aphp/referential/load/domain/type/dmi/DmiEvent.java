package fr.aphp.referential.load.domain.type.dmi;

import static java.util.Arrays.stream;

public enum DmiEvent {
    REGISTER("inscription"),
    DELETE("suppression");

    private final String representation;

    DmiEvent(String representation) {
        this.representation = representation;
    }

    public static DmiEvent fromIdentifier(String representation) {
        return stream(values())
                .filter(dmiEvent -> dmiEvent.representation.equals(representation))
                .findFirst()
                // By default consider as creation ?
                .orElse(REGISTER);
    }
}
