package fr.aphp.referential.load.annotation;

import org.immutables.serial.Serial;
import org.immutables.value.Value.Immutable;
import org.immutables.value.Value.Style;
import org.immutables.value.Value.Style.ImplementationVisibility;

@Style(
        // General
        visibility = ImplementationVisibility.PACKAGE,
        defaultAsDefault = true,
        depluralize = true,
        // Jackson
        forceJacksonPropertyNames = false,
        // Tuple
        allParameters = true,
        defaults = @Immutable(builder = false))
@Serial.Version(1)
public @interface Tupled {}
