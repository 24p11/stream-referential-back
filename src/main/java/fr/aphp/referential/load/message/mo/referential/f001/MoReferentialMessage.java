package fr.aphp.referential.load.message.mo.referential.f001;

import java.util.Date;
import java.util.Optional;

import org.immutables.value.Value;

import fr.aphp.referential.load.annotation.Builded;
import fr.aphp.referential.load.domain.type.mo.referential.MoReferentialEventType;
import fr.aphp.referential.load.message.Message;

import static fr.aphp.referential.load.domain.type.mo.referential.MoReferentialEventType.DELETE;

@Builded
public interface MoReferentialMessage extends Message {
    static Builder builder() {
        return new Builder();
    }

    Date startDate();

    String ucdLabel();

    String ucd7();

    String ucd13();

    MoReferentialEventType moEventType();

    String price();

    String priceTtc();

    String dci();

    String atc();

    @Value.Derived
    default Optional<Date> endDate() {
        if (DELETE == moEventType()) {
            return Optional.of(startDate());
        } else {
            return Optional.empty();
        }
    }

    class Builder extends ImmutableMoReferentialMessage.Builder {}
}
