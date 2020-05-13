package fr.aphp.referential.load.processor.ghmghs.f001;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.camel.Message;
import org.springframework.stereotype.Component;

import fr.aphp.referential.load.bean.MetadataBean;
import fr.aphp.referential.load.domain.type.ghmghs.f001.GhmMetadataType;
import fr.aphp.referential.load.domain.type.ghmghs.f001.GhsMetadataType;
import fr.aphp.referential.load.message.MetadataMessage;
import fr.aphp.referential.load.message.ghmghs.f001.GhmGhsMessage;
import fr.aphp.referential.load.processor.MetadataProcessor;

import static fr.aphp.referential.load.domain.type.SourceType.GHM;
import static fr.aphp.referential.load.domain.type.SourceType.GHS;
import static fr.aphp.referential.load.util.CamelUtils.GHMGHS_F001_METADATA_PROCESSOR;
import static fr.aphp.referential.load.util.CamelUtils.VALIDITY_DATE;

@Component(GHMGHS_F001_METADATA_PROCESSOR)
public class GhmGhsMetadataProcessor implements MetadataProcessor {
    @Override
    public Stream<MetadataMessage> metadataMessageStream(Message message) {
        if (message.getBody() instanceof GhmGhsMessage) {
            GhmGhsMessage ghmGhsMessage = message.getBody(GhmGhsMessage.class);
            Date startDate = ghmGhsMessage.getStartDate().orElse(message.getHeader(VALIDITY_DATE, Date.class));
            MetadataBean.Builder MetadataBeanBuilderGhm = MetadataBean.builder()
                    .vocabularyId(GHM)
                    .conceptCode(ghmGhsMessage.getGhm())
                    .startDate(startDate)
                    .standardConcept(1);
            MetadataBean.Builder MetadataBeanBuilderGhs = MetadataBean.builder()
                    .vocabularyId(GHS)
                    .conceptCode(ghmGhsMessage.getGhs())
                    .startDate(startDate)
                    .standardConcept(1);
            return Stream.concat(ghmMetadataMessageStream(MetadataBeanBuilderGhm, ghmGhsMessage), ghsMetadataMessageStream(MetadataBeanBuilderGhs, ghmGhsMessage));
        } else {
            return Stream.empty();
        }
    }

    private Stream<MetadataMessage> ghmMetadataMessageStream(MetadataBean.Builder metadataBeanBuilderGhm, GhmGhsMessage ghmGhsMessage) {
        return Stream.of(optionalMetadataContentBean(GhmMetadataType.BORNE_BASSE.representation(), ghmGhsMessage.getSeuLow()),
                optionalMetadataContentBean(GhmMetadataType.BORNE_HAUTE.representation(), ghmGhsMessage.getSeuLow()),
                optionalMetadataContentBean(GhmMetadataType.TARIF.representation(), ghmGhsMessage.getGhsPrice()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(metadataContentBean -> MetadataMessage.of(metadataBeanBuilderGhm, metadataContentBean));
    }

    private Stream<MetadataMessage> ghsMetadataMessageStream(MetadataBean.Builder metadataBeanBuilderGhm, GhmGhsMessage ghmGhsMessage) {
        return Stream.of(optionalMetadataContentBean(GhsMetadataType.EXB.representation(), ghmGhsMessage.getExbDaily()),
                optionalMetadataContentBean(GhsMetadataType.EXB_FORFAIT.representation(), ghmGhsMessage.getExbPackage()),
                optionalMetadataContentBean(GhsMetadataType.EXH.representation(), ghmGhsMessage.getExhPrice()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(metadataContentBean -> MetadataMessage.of(metadataBeanBuilderGhm, metadataContentBean));
    }

}
