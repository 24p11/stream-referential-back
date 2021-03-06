package fr.aphp.referential.load.message.ccam.f001;

import java.util.Date;

import fr.aphp.referential.load.annotation.Builded;
import fr.aphp.referential.load.message.Message;

@Builded
public interface CcamMessage extends Message {
    static Builder builder() {
        return new Builder();
    }

    String conceptCode();

    String conceptName();

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
