package fr.aphp.referential.load.message.mo.referential.f001;

import java.util.Date;

import fr.aphp.referential.load.annotation.Builded;
import fr.aphp.referential.load.domain.type.mo.referential.MoReferentialEventType;
import fr.aphp.referential.load.message.Message;

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

    class Builder extends ImmutableMoReferentialMessage.Builder {}
}
