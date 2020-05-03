package fr.aphp.referential.load.util;

import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.camel.support.builder.ValueBuilder;

import static java.util.Arrays.stream;

public final class KeyUtils {
    public static final String DELIMITER = ":";

    private static final Function<String, String[]> keySplit = key -> key.split(DELIMITER);
    // xx:yy -> xx
    public static Function<String, String> keyLeft = key -> keySplit.apply(key)[0];
    // xx:yy -> yy
    public static Function<String, String> keyRight = key -> keySplit.apply(key)[1];

    public static String key(Object... values) {
        return stream(values)
                .map(Objects::toString)
                .collect(Collectors.joining(DELIMITER));
    }

    public static ValueBuilder key(ValueBuilder key1, ValueBuilder key2) {
        return key1.append(DELIMITER).append(key2);
    }
}
