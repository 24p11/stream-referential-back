package fr.aphp.referential.load.processor.mo.indication.f001;

import java.util.Optional;

import fr.aphp.referential.load.message.mo.indication.f001.MoIndicationMessage;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class MoIndicationProcessor {
    public static Optional<MoIndicationMessage> optionalMoIndicationMessage(MoIndicationMessage moIndicationMessage) {
        if (isValidRow(moIndicationMessage)) {
            return Optional.of(moIndicationMessage);
        } else {
            return Optional.empty();
        }
    }

    private static boolean isValidRow(MoIndicationMessage moIndicationMessage) {
        return Optional.ofNullable(moIndicationMessage).isPresent()
                && isNotBlank(moIndicationMessage.getUcd7())
                && !"*".equals(moIndicationMessage.getUcd7());
    }
}
