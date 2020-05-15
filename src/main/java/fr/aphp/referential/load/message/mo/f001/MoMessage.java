package fr.aphp.referential.load.message.mo.f001;

import java.util.Date;

import fr.aphp.referential.load.annotation.Builded;
import fr.aphp.referential.load.domain.type.mo.MoEventType;
import fr.aphp.referential.load.message.Message;

@Builded
public interface MoMessage extends Message {
    static Builder builder() {
        return new Builder();
    }

    Date startDate();

    String ucdLabel();

    String ucd7();

    String ucd13();

    MoEventType moEventType();

    class Builder extends ImmutableMoMessage.Builder {}
}
