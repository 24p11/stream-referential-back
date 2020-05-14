package fr.aphp.referential.load.processor.ccam.f001;

import java.util.Optional;

import fr.aphp.referential.load.bean.ConceptBean;
import fr.aphp.referential.load.message.ccam.f001.CcamMessage;

import static fr.aphp.referential.load.domain.type.SourceType.CCAM;

public class CcamConceptProcessor {
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static Optional<ConceptBean> optionalConceptBean(Optional<CcamMessage> ccamMessageOptional) {
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
