package fr.aphp.referential.load.message.dmi.f001;

import java.util.Date;
import java.util.Optional;

import org.immutables.value.Value.Derived;

import fr.aphp.referential.load.annotation.Builded;
import fr.aphp.referential.load.domain.type.dmi.DmiEvent;
import fr.aphp.referential.load.message.Message;

import static fr.aphp.referential.load.domain.type.dmi.DmiEvent.DELETE;

@Builded
public interface DmiMessage extends Message {
    static Builder builder() {
        return new Builder();
    }

    String lpp();

    String label();

    DmiEvent dmiEvent();

    Date startDate();

    @Derived
    default Optional<Date> endDate() {
        if (DELETE == dmiEvent()) {
            return Optional.of(startDate());
        } else {
            return Optional.empty();
        }
    }

    class Builder extends ImmutableDmiMessage.Builder {}
}
