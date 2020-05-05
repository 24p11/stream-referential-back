package fr.aphp.referential.load.processor.cim10.f001;

import java.util.Date;
import java.util.Optional;

import org.apache.camel.Message;

import fr.aphp.referential.load.bean.ConceptBean;
import fr.aphp.referential.load.message.cim10.f001.Cim10Message;

import static fr.aphp.referential.load.domain.type.SourceType.CIM10;
import static fr.aphp.referential.load.util.CamelUtils.VALIDITY_DATE;

public class Cim10ConceptProcessor {
    public static Optional<ConceptBean> optionalConceptBean(Message message) {
        if (message.getBody() instanceof Cim10Message) {
            Cim10Message cim10F001Message = message.getBody(Cim10Message.class);
            return Optional.of(ConceptBean.builder()
                    .vocabularyId(CIM10)
                    .conceptCode(cim10F001Message.getConceptCode())
                    .conceptName(cim10F001Message.getConceptName())
                    .startDate(message.getHeader(VALIDITY_DATE, Date.class))
                    .build());
        } else {
            return Optional.empty();
        }
    }
}
