package fr.aphp.referential.load.processor.ghgghs.f001;

import java.util.Date;
import java.util.stream.Stream;

import org.apache.camel.Message;

import fr.aphp.referential.load.bean.ConceptBean;
import fr.aphp.referential.load.message.ghmghs.f001.GhmGhsMessage;

import static fr.aphp.referential.load.domain.type.SourceType.GHM;
import static fr.aphp.referential.load.domain.type.SourceType.GHS;
import static fr.aphp.referential.load.util.CamelUtils.SOURCE_TYPE;
import static fr.aphp.referential.load.util.CamelUtils.VALIDITY_DATE;

public class GhmGhsProcessor {
    public static Stream<ConceptBean> streamConceptBean(Message message) {
        if (message.getBody() instanceof GhmGhsMessage) {
            GhmGhsMessage ghmGhsMessage = message.getBody(GhmGhsMessage.class);
            Date startDate = ghmGhsMessage.getStartDate().orElse(message.getHeader(VALIDITY_DATE, Date.class));
            ConceptBean ghs = ConceptBean.builder()
                    .vocabularyId(GHS)
                    .conceptCode(ghmGhsMessage.getGhs())
                    .conceptName(GHS.name())
                    .standardConcept(1)
                    .startDate(startDate)
                    .build();
            ConceptBean ghm = ConceptBean.builder()
                    .vocabularyId(GHM)
                    .conceptCode(ghmGhsMessage.getGhm())
                    .conceptName(ghmGhsMessage.getGhmLabel())
                    .standardConcept(1)
                    .startDate(startDate)
                    .build();
            return Stream.of(ghs, ghm);
        } else {
            return Stream.empty();
        }
    }

    public static void setSourceTypeHeader(Message message) {
        ConceptBean conceptBean = message.getBody(ConceptBean.class);
        message.setHeader(SOURCE_TYPE, conceptBean.vocabularyId());
    }
}
