package fr.aphp.referential.load.message.dmi.f001;

import java.util.Date;

import fr.aphp.referential.load.annotation.Builded;
import fr.aphp.referential.load.domain.type.dmi.DmiEvent;
import fr.aphp.referential.load.message.Message;

@Builded
public interface DmiMessage extends Message {
    static Builder builder() {
        return new Builder();
    }

    Date startDate();

    String label();

    String lpp();

    DmiEvent dmiEvent();

    class Builder extends ImmutableDmiMessage.Builder {}
}
