package fr.aphp.referential.load.processor.ghmghs.f001;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.camel.Message;
import org.springframework.stereotype.Component;

import fr.aphp.referential.load.bean.MetadataBean;
import fr.aphp.referential.load.message.MetadataMessage;
import fr.aphp.referential.load.message.ghmghs.f001.GhmGhsMessage;
import fr.aphp.referential.load.processor.MetadataProcessor;

import static fr.aphp.referential.load.domain.type.SourceType.GHM;
import static fr.aphp.referential.load.domain.type.SourceType.GHS;
import static fr.aphp.referential.load.domain.type.ghmghs.f001.GhmMetadataType.BORNE_BASSE;
import static fr.aphp.referential.load.domain.type.ghmghs.f001.GhmMetadataType.BORNE_HAUTE;
import static fr.aphp.referential.load.domain.type.ghmghs.f001.GhsMetadataType.EXB;
import static fr.aphp.referential.load.domain.type.ghmghs.f001.GhsMetadataType.EXB_FORFAIT;
import static fr.aphp.referential.load.domain.type.ghmghs.f001.GhsMetadataType.EXH;
import static fr.aphp.referential.load.domain.type.ghmghs.f001.GhsMetadataType.TARIF;
import static fr.aphp.referential.load.util.CamelUtils.GHMGHS_F001_METADATA_PROCESSOR;
import static fr.aphp.referential.load.util.CamelUtils.VALIDITY_DATE;

@Component(GHMGHS_F001_METADATA_PROCESSOR)
public class GhmGhsMetadataProcessor implements MetadataProcessor {
    @Override
    public Stream<MetadataMessage> metadataMessageStream(Message message) {
        if (message.getBody() instanceof GhmGhsMessage) {
            GhmGhsMessage ghmGhsMessage = message.getBody(GhmGhsMessage.class);
            Date startDate = ghmGhsMessage.getStartDate().orElse(message.getHeader(VALIDITY_DATE, Date.class));
            MetadataBean metadataBeanGhm = MetadataBean.builder()
                    .vocabularyId(GHM)
                    .conceptCode(ghmGhsMessage.getGhm())
                    .startDate(startDate)
                    .standardConcept(1)
                    .build();
            MetadataBean metadataBeanGhs = MetadataBean.builder()
                    .vocabularyId(GHS)
                    .conceptCode(ghmGhsMessage.getGhs())
                    .startDate(startDate)
                    .standardConcept(1)
                    .build();
            return Stream.concat(metadataMessageStreamGhm(metadataBeanGhm, ghmGhsMessage), metadataMessageStreamGhs(metadataBeanGhs, ghmGhsMessage));
        } else {
            return Stream.empty();
        }
    }

    private Stream<MetadataMessage> metadataMessageStreamGhm(MetadataBean metadataBeanBuilderGhm, GhmGhsMessage ghmGhsMessage) {
        return Stream.of(
                metadataContentBeanOptional(BORNE_BASSE.representation(), ghmGhsMessage.getSeuLow()),
                metadataContentBeanOptional(BORNE_HAUTE.representation(), ghmGhsMessage.getSeuLow()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(metadataContentBean -> MetadataMessage.of(metadataBeanBuilderGhm, metadataContentBean));
    }

    private Stream<MetadataMessage> metadataMessageStreamGhs(MetadataBean metadataBeanBuilderGhs, GhmGhsMessage ghmGhsMessage) {
        return Stream.of(
                metadataContentBeanOptional(TARIF.representation(), ghmGhsMessage.getGhsPrice()),
                metadataContentBeanOptional(EXB.representation(), ghmGhsMessage.getExbDaily()),
                metadataContentBeanOptional(EXB_FORFAIT.representation(), ghmGhsMessage.getExbPackage()),
                metadataContentBeanOptional(EXH.representation(), ghmGhsMessage.getExhPrice()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(metadataContentBean -> MetadataMessage.of(metadataBeanBuilderGhs, metadataContentBean));
    }

}
