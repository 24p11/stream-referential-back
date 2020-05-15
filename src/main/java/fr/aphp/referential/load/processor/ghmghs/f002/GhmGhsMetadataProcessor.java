package fr.aphp.referential.load.processor.ghmghs.f002;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.camel.Message;
import org.springframework.stereotype.Component;

import fr.aphp.referential.load.bean.MetadataBean;
import fr.aphp.referential.load.domain.type.ghmghs.f002.GhmMetadataType;
import fr.aphp.referential.load.message.MetadataMessage;
import fr.aphp.referential.load.message.ghmghs.f002.GhmGhsMessage;
import fr.aphp.referential.load.processor.MetadataProcessor;

import static fr.aphp.referential.load.domain.type.SourceType.GHM;
import static fr.aphp.referential.load.util.CamelUtils.GHMGHS_F002_METADATA_PROCESSOR;
import static fr.aphp.referential.load.util.CamelUtils.VALIDITY_DATE;

@Component(GHMGHS_F002_METADATA_PROCESSOR)
public class GhmGhsMetadataProcessor implements MetadataProcessor {
    @Override
    public Stream<MetadataMessage> metadataMessageStream(Message message) {
        if (message.getBody() instanceof GhmGhsMessage) {
            GhmGhsMessage ghmGhsMessage = message.getBody(GhmGhsMessage.class);
            MetadataBean.Builder MetadataBeanBuilderGhm = MetadataBean.builder()
                    .vocabularyId(GHM)
                    .conceptCode(ghmGhsMessage.getGhm())
                    .startDate(message.getHeader(VALIDITY_DATE, Date.class))
                    .standardConcept(1);
            return Stream.of(metadataContentBeanOptional(GhmMetadataType.TARIF.representation(), ghmGhsMessage.getPrice()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(metadataContentBean -> MetadataMessage.of(MetadataBeanBuilderGhm, metadataContentBean));
        } else {
            return Stream.empty();
        }
    }
}
