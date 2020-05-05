package fr.aphp.referential.load.processor.ccam.f002;

import java.util.Date;
import java.util.Optional;

import org.apache.camel.Message;

import fr.aphp.referential.load.bean.ReferentialBean;
import fr.aphp.referential.load.message.ccam.f002.CcamMessage;

import static fr.aphp.referential.load.domain.type.SourceType.CCAM;
import static fr.aphp.referential.load.util.CamelUtils.VALIDITY_DATE;

public class CcamConceptProcessor {
    @SuppressWarnings("unchecked")
    public static Optional<ReferentialBean> optionalReferentialBean(Message message) {
        if (message.getBody() instanceof CcamMessage) {
            CcamMessage ccamMessage = message.getBody(CcamMessage.class);
            return Optional.of(ReferentialBean.builder()
                    .type(CCAM)
                    .domainId(ccamMessage.getDomainId())
                    .label(ccamMessage.getLabel())
                    .startDate(message.getHeader(VALIDITY_DATE, Date.class))
                    .build());
        } else {
            return Optional.empty();
        }
    }
}
