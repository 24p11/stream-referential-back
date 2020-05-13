package fr.aphp.referential.load.processor.ghmghs.f002;

import java.util.Date;
import java.util.Optional;

import org.apache.camel.Message;

import fr.aphp.referential.load.bean.ConceptBean;
import fr.aphp.referential.load.message.ghmghs.f002.GhmGhsMessage;

import static fr.aphp.referential.load.domain.type.SourceType.GHM;
import static fr.aphp.referential.load.util.CamelUtils.SOURCE_TYPE;
import static fr.aphp.referential.load.util.CamelUtils.VALIDITY_DATE;

public class GhmGhsConceptProcessor {
    public static Optional<ConceptBean> optionalConceptBean(Message message) {
        if (message.getBody() instanceof GhmGhsMessage) {
            GhmGhsMessage ghmGhsMessage = message.getBody(GhmGhsMessage.class);
            ConceptBean conceptBeanGhm = ConceptBean.builder()
                    .vocabularyId(GHM)
                    .conceptCode(ghmGhsMessage.getGhm())
                    .conceptName(GHM.name())
                    .standardConcept(1)
                    .startDate(message.getHeader(VALIDITY_DATE, Date.class))
                    .build();
            message.setHeader(SOURCE_TYPE, conceptBeanGhm.vocabularyId());
            return Optional.of(conceptBeanGhm);
        } else {
            return Optional.empty();
        }
    }
}
