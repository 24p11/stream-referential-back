package fr.aphp.referential.load.processor.ccam.f003;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.camel.Message;

import fr.aphp.referential.load.bean.MetadataBean;
import fr.aphp.referential.load.message.ccam.f003.CcamMessage;

import static fr.aphp.referential.load.domain.type.SourceType.CCAM;
import static fr.aphp.referential.load.domain.type.ccam.f003.CcamMetadataType.EXTENSION_CODES;
import static fr.aphp.referential.load.util.CamelUtils.VALIDITY_DATE;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class CcamMetadataProcessor {
    @SuppressWarnings("unchecked")
    public static Stream<MetadataBean> metadataBeanStream(Message message) {
        if (message.getBody() instanceof CcamMessage) {
            CcamMessage ccamMessage = message.getBody(CcamMessage.class);
            return Stream.of(metadataBuilderOptional(EXTENSION_CODES.representation(), ccamMessage.getExtensionCodes()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(metadata -> metadata.conceptCode(ccamMessage.getConceptCode()))
                    .map(metadata -> metadata.startDate(message.getHeader(VALIDITY_DATE, Date.class)))
                    .map(MetadataBean.Builder::build);
        } else {
            return Stream.empty();
        }
    }

    private static Optional<MetadataBean.Builder> metadataBuilderOptional(String entry, String value) {
        if (isNotBlank(value)) {
            return Optional.of(MetadataBean.builder()
                    .vocabularyId(CCAM)
                    .entry(entry)
                    .value(value));
        } else {
            return Optional.empty();
        }
    }
}
