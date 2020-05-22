package fr.aphp.referential.load.processor.ccam.f002;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.camel.Message;
import org.springframework.stereotype.Component;

import fr.aphp.referential.load.bean.MetadataBean;
import fr.aphp.referential.load.message.MetadataMessage;
import fr.aphp.referential.load.message.ccam.f002.CcamMessage;
import fr.aphp.referential.load.processor.MetadataProcessor;

import static fr.aphp.referential.load.domain.type.SourceType.CCAM;
import static fr.aphp.referential.load.domain.type.ccam.f002.CcamMetadataType.ACTIVITY;
import static fr.aphp.referential.load.domain.type.ccam.f002.CcamMetadataType.EXTENSION;
import static fr.aphp.referential.load.domain.type.ccam.f002.CcamMetadataType.PHASE;
import static fr.aphp.referential.load.util.CamelUtils.CCAM_F002_METADATA_PROCESSOR;
import static fr.aphp.referential.load.util.CamelUtils.VALIDITY_DATE;

@Component(CCAM_F002_METADATA_PROCESSOR)
public class CcamMetadataProcessor implements MetadataProcessor {
    @Override
    public Stream<MetadataMessage> metadataMessageStream(Message message) {
        if (message.getBody() instanceof CcamMessage) {
            CcamMessage ccamMessage = message.getBody(CcamMessage.class);
            MetadataBean metadataBean = MetadataBean.builder()
                    .vocabularyId(CCAM)
                    .conceptCode(ccamMessage.getConceptCode())
                    .startDate(message.getHeader(VALIDITY_DATE, Date.class))
                    .standardConcept(1)
                    .build();
            return Stream.of(metadataContentBeanOptional(PHASE.representation(), ccamMessage.getPhase()),
                    metadataContentBeanOptional(ACTIVITY.representation(), ccamMessage.getActivity()),
                    metadataContentBeanOptional(EXTENSION.representation(), ccamMessage.getExtension()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(metadataContentBean -> MetadataMessage.of(metadataBean, metadataContentBean));
        } else {
            return Stream.empty();
        }
    }
}
