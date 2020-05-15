package fr.aphp.referential.load.processor.mo.f001;

import java.util.Optional;
import java.util.stream.Stream;

import org.apache.camel.Message;
import org.springframework.stereotype.Component;

import fr.aphp.referential.load.bean.MetadataBean;
import fr.aphp.referential.load.message.MetadataMessage;
import fr.aphp.referential.load.message.mo.f001.MoMessage;
import fr.aphp.referential.load.processor.MetadataProcessor;

import static fr.aphp.referential.load.domain.type.SourceType.MO;
import static fr.aphp.referential.load.domain.type.dmi.f001.DmiMetadataType.LISTE_EN_SUS;
import static fr.aphp.referential.load.domain.type.dmi.f001.DmiMetadataType.OUI;
import static fr.aphp.referential.load.util.CamelUtils.MO_F001_METADATA_PROCESSOR;
import static java.util.stream.Stream.empty;
import static java.util.stream.Stream.of;

@Component(MO_F001_METADATA_PROCESSOR)
public class MoMetadataProcessor implements MetadataProcessor {
    @SuppressWarnings("unchecked")
    @Override
    public Stream<MetadataMessage> metadataMessageStream(Message message) {
        Optional<MoMessage> moMessageOptional = message.getBody(Optional.class);
        if (moMessageOptional.isPresent()) {
            MoMessage moMessage = moMessageOptional.get();
            MetadataBean.Builder metadataBeanBuilder = MetadataBean.builder()
                    .vocabularyId(MO)
                    .conceptCode(moMessage.ucd7())
                    .standardConcept(1)
                    .startDate(moMessage.startDate());
            return of(metadataContentBeanOptional(LISTE_EN_SUS.representation(), OUI.representation()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(metadataContentBean -> MetadataMessage.of(metadataBeanBuilder, metadataContentBean));
        } else {
            return empty();
        }
    }
}
