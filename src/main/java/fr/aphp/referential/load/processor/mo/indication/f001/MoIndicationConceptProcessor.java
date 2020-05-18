package fr.aphp.referential.load.processor.mo.indication.f001;

import java.util.Date;
import java.util.stream.Stream;

import org.apache.camel.Message;

import fr.aphp.referential.load.bean.ConceptBean;
import fr.aphp.referential.load.bean.ConceptRelationshipBean;
import fr.aphp.referential.load.message.mo.indication.f001.MoIndicationMessage;
import fr.aphp.referential.load.util.KeyUtils;

import static fr.aphp.referential.load.domain.type.SourceType.MO_INDICATION;
import static fr.aphp.referential.load.domain.type.SourceType.MO_REFERENTIAL;
import static fr.aphp.referential.load.util.CamelUtils.VALIDITY_DATE;

public class MoIndicationConceptProcessor {
    public static Stream<?> streamBean(Message message) {
        if (message.getBody() instanceof MoIndicationMessage) {
            MoIndicationMessage moIndicationMessage = message.getBody(MoIndicationMessage.class);
            Date startDate = moIndicationMessage.getStartDate().orElse(message.getHeader(VALIDITY_DATE, Date.class));
            ConceptBean conceptBean = ConceptBean.builder()
                    .vocabularyId(MO_INDICATION)
                    .conceptCode(moIndicationMessage.getUcd7())
                    .conceptName(MO_INDICATION.name())
                    .standardConcept(1)
                    .startDate(startDate)
                    .build();
            ConceptRelationshipBean conceptRelationshipBean = ConceptRelationshipBean.of(KeyUtils.key(MO_REFERENTIAL, conceptBean.conceptCode()), conceptBean.conceptId());
            return Stream.of(conceptBean, conceptRelationshipBean);
        } else {
            return Stream.empty();
        }
    }
}
