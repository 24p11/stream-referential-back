package fr.aphp.referential.load.processor.ccam.f003;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.camel.Message;
import org.springframework.stereotype.Component;

import fr.aphp.referential.load.bean.MetadataBean;
import fr.aphp.referential.load.message.MetadataMessage;
import fr.aphp.referential.load.message.ccam.f003.CcamMessage;
import fr.aphp.referential.load.processor.MetadataProcessor;

import static fr.aphp.referential.load.domain.type.SourceType.CCAM;
import static fr.aphp.referential.load.domain.type.ccam.f003.CcamMetadataType.EXTENSION_CODES;
import static fr.aphp.referential.load.util.CamelUtils.CCAM_F003_METADATA_PROCESSOR;
import static fr.aphp.referential.load.util.CamelUtils.VALIDITY_DATE;

@Component(CCAM_F003_METADATA_PROCESSOR)
public class CcamMetadataProcessor implements MetadataProcessor {
    @Override
    public Stream<Optional<MetadataMessage>> optionalMetadataMessageStream(Message message) {
        if (message.getBody() instanceof CcamMessage) {
            CcamMessage ccamMessage = message.getBody(CcamMessage.class);
            MetadataBean.Builder MetadataBeanBuilder = MetadataBean.builder()
                    .vocabularyId(CCAM)
                    .conceptCode(ccamMessage.getConceptCode())
                    .startDate(message.getHeader(VALIDITY_DATE, Date.class))
                    .standardConcept(1);
            return Stream.of(optionalMetadataContentBean(EXTENSION_CODES.representation(), ccamMessage.getExtensionCodes()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(metadataContentBean -> MetadataMessage.of(MetadataBeanBuilder, metadataContentBean))
                    .map(Optional::of);
        } else {
            return Stream.of(Optional.empty());
        }
    }
}
