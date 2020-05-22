package fr.aphp.referential.load.processor.mo.referential.f001;

import java.util.Optional;
import java.util.stream.Stream;

import org.apache.camel.Message;
import org.springframework.stereotype.Component;

import fr.aphp.referential.load.bean.MetadataBean;
import fr.aphp.referential.load.message.MetadataMessage;
import fr.aphp.referential.load.message.mo.referential.f001.MoReferentialMessage;
import fr.aphp.referential.load.processor.MetadataProcessor;

import static fr.aphp.referential.load.domain.type.SourceType.MO_REFERENTIAL;
import static fr.aphp.referential.load.domain.type.mo.referential.f001.MoReferentialMetadataType.ATC;
import static fr.aphp.referential.load.domain.type.mo.referential.f001.MoReferentialMetadataType.DCI;
import static fr.aphp.referential.load.domain.type.mo.referential.f001.MoReferentialMetadataType.LISTE_EN_SUS;
import static fr.aphp.referential.load.domain.type.mo.referential.f001.MoReferentialMetadataType.OUI;
import static fr.aphp.referential.load.domain.type.mo.referential.f001.MoReferentialMetadataType.PRICE;
import static fr.aphp.referential.load.domain.type.mo.referential.f001.MoReferentialMetadataType.PRICE_TTC;
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
                    .vocabularyId(MO_REFERENTIAL)
                    .conceptCode(moReferentialMessage.ucd7())
                    .standardConcept(1)
                    .startDate(moReferentialMessage.startDate());
            moReferentialMessage.endDate().ifPresent(metadataBeanBuilder::endDate);
            return of(metadataContentBeanOptional(LISTE_EN_SUS.representation(), OUI.representation()),
                    metadataContentBeanOptional(PRICE.representation(), moReferentialMessage.price()),
                    metadataContentBeanOptional(PRICE_TTC.representation(), moReferentialMessage.priceTtc()),
                    metadataContentBeanOptional(DCI.representation(), moReferentialMessage.dci()),
                    metadataContentBeanOptional(ATC.representation(), moReferentialMessage.atc()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(metadataContentBean -> MetadataMessage.of(metadataBeanBuilder.build(), metadataContentBean));
        } else {
            return empty();
        }
    }
}
