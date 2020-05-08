package fr.aphp.referential.load.processor.ccam.f001;

import java.util.Optional;

import org.apache.camel.Message;

import fr.aphp.referential.load.bean.ConceptBean;
import fr.aphp.referential.load.message.ccam.f001.CcamMessage;

import static fr.aphp.referential.load.domain.type.SourceType.CCAM;

public class CcamConceptProcessor {
    @SuppressWarnings("unchecked")
    public static Optional<ConceptBean> optionalConceptBean(Message message) {
        Optional<CcamMessage> ccamMessageOptional = message.getBody(Optional.class);
        return ccamMessageOptional.map(CcamConceptProcessor::conceptBean);
    }

    private static ConceptBean conceptBean(CcamMessage ccamMessage) {
        return ConceptBean.builder()
                .vocabularyId(CCAM)
                .conceptCode(ccamMessage.conceptCode())
                .conceptName(ccamMessage.conceptName())
                .standardConcept(1)
                .startDate(ccamMessage.startDate())
                .build();
    }
}
