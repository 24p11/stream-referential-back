package fr.aphp.referential.load.processor.mo.referential.f001;

import java.util.Optional;
import java.util.stream.Stream;

import org.apache.camel.Message;
import org.springframework.stereotype.Component;

import fr.aphp.referential.load.bean.MetadataBean;
import fr.aphp.referential.load.message.MetadataMessage;
import fr.aphp.referential.load.message.mo.referential.f001.MoReferentialMessage;
import fr.aphp.referential.load.processor.MetadataProcessor;

import static fr.aphp.referential.load.domain.type.SourceType.MO;
import static fr.aphp.referential.load.domain.type.dmi.f001.DmiMetadataType.LISTE_EN_SUS;
import static fr.aphp.referential.load.domain.type.dmi.f001.DmiMetadataType.OUI;
import static fr.aphp.referential.load.util.CamelUtils.MO_REFERENTIAL_F001_METADATA_PROCESSOR;
import static java.util.stream.Stream.empty;
import static java.util.stream.Stream.of;

@Component(MO_REFERENTIAL_F001_METADATA_PROCESSOR)
public class MoReferentialMetadataProcessor implements MetadataProcessor {
    @SuppressWarnings("unchecked")
    @Override
    public Stream<MetadataMessage> metadataMessageStream(Message message) {
        Optional<MoReferentialMessage> moReferentialMessageOptional = message.getBody(Optional.class);
        if (moReferentialMessageOptional.isPresent()) {
            MoReferentialMessage moReferentialMessage = moReferentialMessageOptional.get();
            MetadataBean.Builder metadataBeanBuilder = MetadataBean.builder()
                    .vocabularyId(MO)
                    .conceptCode(moReferentialMessage.ucd7())
                    .standardConcept(1)
                    .startDate(moReferentialMessage.startDate());
            return of(metadataContentBeanOptional(LISTE_EN_SUS.representation(), OUI.representation()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(metadataContentBean -> MetadataMessage.of(metadataBeanBuilder, metadataContentBean));
        } else {
            return empty();
        }
    }
}
