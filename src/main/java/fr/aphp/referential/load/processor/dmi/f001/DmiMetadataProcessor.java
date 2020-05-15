package fr.aphp.referential.load.processor.dmi.f001;

import java.util.Optional;
import java.util.stream.Stream;

import org.apache.camel.Message;
import org.springframework.stereotype.Component;

import fr.aphp.referential.load.bean.MetadataBean;
import fr.aphp.referential.load.message.MetadataMessage;
import fr.aphp.referential.load.message.dmi.f001.DmiMessage;
import fr.aphp.referential.load.processor.MetadataProcessor;

import static fr.aphp.referential.load.domain.type.SourceType.DMI;
import static fr.aphp.referential.load.domain.type.dmi.f001.DmiMetadataType.LISTE_EN_SUS;
import static fr.aphp.referential.load.domain.type.dmi.f001.DmiMetadataType.OUI;
import static fr.aphp.referential.load.util.CamelUtils.DMI_F001_METADATA_PROCESSOR;
import static java.util.stream.Stream.empty;
import static java.util.stream.Stream.of;

@Component(DMI_F001_METADATA_PROCESSOR)
public class DmiMetadataProcessor implements MetadataProcessor {
    @Override
    @SuppressWarnings("unchecked")
    public Stream<MetadataMessage> metadataMessageStream(Message message) {
        Optional<DmiMessage> dmiMessageOptional = message.getBody(Optional.class);
        if (dmiMessageOptional.isPresent()) {
            DmiMessage dmiMessage = dmiMessageOptional.get();
            MetadataBean.Builder metadataBeanBuilder = MetadataBean.builder()
                    .vocabularyId(DMI)
                    .conceptCode(dmiMessage.lpp())
                    .standardConcept(1)
                    .startDate(dmiMessage.startDate());
            dmiMessage.endDate().ifPresent(metadataBeanBuilder::endDate);
            return of(metadataContentBeanOptional(LISTE_EN_SUS.representation(), OUI.representation()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(metadataContentBean -> MetadataMessage.of(metadataBeanBuilder, metadataContentBean));
        } else {
            return empty();
        }
    }
}
