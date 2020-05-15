package fr.aphp.referential.load.processor.dmi.f001;

import java.util.Optional;

import fr.aphp.referential.load.bean.ConceptBean;
import fr.aphp.referential.load.message.dmi.f001.DmiMessage;

import static fr.aphp.referential.load.domain.type.SourceType.DMI;

public class DmiConceptProcessor {
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static Optional<ConceptBean> optionalConceptBean(Optional<DmiMessage> dmiMessageOptional) {
        return dmiMessageOptional.map(DmiConceptProcessor::conceptBean);
    }

    private static ConceptBean conceptBean(DmiMessage dmiMessage) {
        ConceptBean.Builder conceptBeanBuilder = ConceptBean.builder()
                .vocabularyId(DMI)
                .conceptCode(dmiMessage.lpp())
                .conceptName(dmiMessage.label())
                .standardConcept(1)
                .startDate(dmiMessage.startDate());
        dmiMessage.endDate().ifPresent(conceptBeanBuilder::endDate);
        return conceptBeanBuilder.build();
    }
}
