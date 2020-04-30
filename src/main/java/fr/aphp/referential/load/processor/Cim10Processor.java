package fr.aphp.referential.load.processor;

import fr.aphp.referential.load.domain.type.Cim10Type;

import static java.lang.String.format;

public class Cim10Processor {
    public static <T> Class<?> forVersion(Cim10Type cim10Type) throws ClassNotFoundException {
        return Class.forName(format("fr.aphp.referential.load.message.Cim10%sMessage", cim10Type));
    }
}
