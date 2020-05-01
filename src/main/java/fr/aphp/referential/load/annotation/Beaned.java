package fr.aphp.referential.load.annotation;

import org.immutables.serial.Serial;
import org.immutables.value.Value.Style;

@Style(
        // General
        defaultAsDefault = true,
        depluralize = true,
        // Bean
        create = "new",
        typeImmutable = "Immutable*Bean",
        // Hide
        isInitialized = "wasInitialized",
        typeModifiable = "*Bean")
@Serial.Version(1)
public @interface Beaned {}
