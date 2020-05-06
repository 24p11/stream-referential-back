package fr.aphp.referential.load.processor.ccam.f002;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.camel.Message;

import fr.aphp.referential.load.bean.MetadataBean;
import fr.aphp.referential.load.message.ccam.f002.CcamMessage;

import static fr.aphp.referential.load.domain.type.SourceType.CCAM;
import static fr.aphp.referential.load.domain.type.ccam.f002.CcamMetadataType.ACTIVITY;
import static fr.aphp.referential.load.domain.type.ccam.f002.CcamMetadataType.EXTENSION;
import static fr.aphp.referential.load.domain.type.ccam.f002.CcamMetadataType.PHASE;
import static fr.aphp.referential.load.util.CamelUtils.VALIDITY_DATE;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class CcamMetadataProcessor {
    @SuppressWarnings("unchecked")
    public static Stream<MetadataBean> metadataBeanStream(Message message) {
        if (message.getBody() instanceof CcamMessage) {
            CcamMessage ccamMessage = message.getBody(CcamMessage.class);
            return Stream.of(metadataBuilderOptional(PHASE.representation(), ccamMessage.getPhase()),
                    metadataBuilderOptional(ACTIVITY.representation(), ccamMessage.getActivity()),
                    metadataBuilderOptional(EXTENSION.representation(), ccamMessage.getExtension()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(metadata -> metadata.conceptCode(ccamMessage.getConceptCode()))
                    .map(metadata -> metadata.startDate(message.getHeader(VALIDITY_DATE, Date.class)))
                    .map(MetadataBean.Builder::build);
        } else {
            return Stream.empty();
        }
    }

    private static Optional<MetadataBean.Builder> metadataBuilderOptional(String name, String value) {
        if (isNotBlank(value)) {
            return Optional.of(MetadataBean.builder()
                    .vocabularyId(CCAM)
                    .name(name)
                    .value(value));
        } else {
            return Optional.empty();
        }
    }
}
