package fr.aphp.referential.load.processor.mo.f001;

import java.util.Optional;

import fr.aphp.referential.load.bean.ConceptBean;
import fr.aphp.referential.load.message.mo.f001.MoMessage;

import static fr.aphp.referential.load.domain.type.SourceType.MO;

public class MoConceptProcessor {
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static Optional<ConceptBean> optionalConceptBean(Optional<MoMessage> dmiMessageOptional) {
        return dmiMessageOptional.map(MoConceptProcessor::conceptBean);
    }

    private static ConceptBean conceptBean(MoMessage moMessage) {
        ConceptBean.Builder conceptBeanBuilder = ConceptBean.builder()
                .vocabularyId(MO)
                .conceptCode(moMessage.ucd7())
                .conceptName(moMessage.ucdLabel())
                .standardConcept(1)
                .startDate(moMessage.startDate());
        return conceptBeanBuilder.build();
    }
}
