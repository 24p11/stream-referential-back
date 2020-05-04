package fr.aphp.referential.load.message.ccam.f001;

import java.util.Date;

import fr.aphp.referential.load.annotation.Builded;
import fr.aphp.referential.load.domain.type.SourceType;

@Builded
public interface CcamMessage {
    static Builder builder() {
        return new Builder();
    }

    SourceType type();

    String domainId();

    String label();

    Date startDate();

    String extensionPmsi();

    String codePmsi();

    String compHas();

    String consignPmsi();

    String modificationType();

    String modificationVersion();

    String rsc();

    String ap();

    String etm();

    String rgt();

    String classifying();

    String billingList();

    String icr();

    String icrPrivate();

    String icrA4();

    String icrAnapath();

    String icrRea();

    String modifier();

    String gestCompAnes();

    String gestComp();

    String denom();

    class Builder extends ImmutableCcamMessage.Builder {}
}
