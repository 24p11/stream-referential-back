package fr.aphp.referential.load.processor.mo.referential.f001;

import java.util.Optional;

import fr.aphp.referential.load.bean.ConceptBean;
import fr.aphp.referential.load.message.mo.referential.f001.MoReferentialMessage;

import static fr.aphp.referential.load.domain.type.SourceType.MO_REFERENTIAL;

public class MoReferentialConceptProcessor {
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static Optional<ConceptBean> optionalConceptBean(Optional<MoReferentialMessage> dmiMessageOptional) {
        return dmiMessageOptional.map(MoReferentialConceptProcessor::conceptBean);
    }

    private static ConceptBean conceptBean(MoReferentialMessage moReferentialMessage) {
        ConceptBean.Builder conceptBeanBuilder = ConceptBean.builder()
                .vocabularyId(MO_REFERENTIAL)
                .conceptCode(moReferentialMessage.ucd7())
                .conceptName(moReferentialMessage.ucdLabel())
                .standardConcept(1)
                .startDate(moReferentialMessage.startDate());
        return conceptBeanBuilder.build();
    }
}
