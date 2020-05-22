package fr.aphp.referential.load.processor.mo.indication.f001;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.camel.Message;

import fr.aphp.referential.load.bean.ConceptBean;
import fr.aphp.referential.load.bean.ConceptRelationshipBean;
import fr.aphp.referential.load.message.mo.indication.f001.MoIndicationMessage;
import fr.aphp.referential.load.util.KeyUtils;

import static fr.aphp.referential.load.domain.type.SourceType.MO_INDICATION;
import static fr.aphp.referential.load.domain.type.SourceType.MO_REFERENTIAL;
import static fr.aphp.referential.load.util.CamelUtils.VALIDITY_DATE;
import static java.util.stream.Stream.of;

public class MoIndicationConceptProcessor {
    @SuppressWarnings("unchecked")
    public static Stream<?> streamBean(Message message) {
        Optional<MoIndicationMessage> optionalMoIndicationMessage = message.getBody(Optional.class);
        if (optionalMoIndicationMessage.isPresent()) {
            MoIndicationMessage moIndicationMessage = optionalMoIndicationMessage.get();
            Date startDate = moIndicationMessage.getStartDate().orElse(message.getHeader(VALIDITY_DATE, Date.class));
            ConceptBean.Builder conceptBeanBuilder = ConceptBean.builder()
                    .vocabularyId(MO_INDICATION)
                    .conceptCode(moIndicationMessage.getLes())
                    .conceptName(MO_INDICATION.name())
                    .standardConcept(1)
                    .startDate(startDate);
            moIndicationMessage.getEndDate().ifPresent(conceptBeanBuilder::endDate);
            ConceptBean conceptBean = conceptBeanBuilder.build();
            ConceptRelationshipBean conceptRelationshipBean = ConceptRelationshipBean.of(KeyUtils.key(MO_REFERENTIAL, moIndicationMessage.getUcd7()), conceptBean.conceptId());
            return of(conceptBean, conceptRelationshipBean);
        } else {
            return Stream.empty();
        }
    }
}
