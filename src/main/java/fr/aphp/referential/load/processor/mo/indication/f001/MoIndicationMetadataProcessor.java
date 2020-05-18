package fr.aphp.referential.load.processor.mo.indication.f001;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.camel.Message;
import org.springframework.stereotype.Component;

import fr.aphp.referential.load.bean.MetadataBean;
import fr.aphp.referential.load.domain.type.mo.indication.f001.MoIndicationMetadataType;
import fr.aphp.referential.load.message.MetadataMessage;
import fr.aphp.referential.load.message.mo.indication.f001.MoIndicationMessage;
import fr.aphp.referential.load.processor.MetadataProcessor;

import static fr.aphp.referential.load.domain.type.SourceType.MO_INDICATION;
import static fr.aphp.referential.load.domain.type.mo.indication.f001.MoIndicationMetadataType.CLASS_IND_1;
import static fr.aphp.referential.load.domain.type.mo.indication.f001.MoIndicationMetadataType.CLASS_IND_2;
import static fr.aphp.referential.load.domain.type.mo.indication.f001.MoIndicationMetadataType.INSCRIPTION;
import static fr.aphp.referential.load.util.CamelUtils.MO_INDICATION_F001_METADATA_PROCESSOR;
import static fr.aphp.referential.load.util.CamelUtils.VALIDITY_DATE;
import static java.util.stream.Stream.of;

@Component(MO_INDICATION_F001_METADATA_PROCESSOR)
public class MoIndicationMetadataProcessor implements MetadataProcessor {
    @SuppressWarnings("unchecked")
    @Override
    public Stream<MetadataMessage> metadataMessageStream(Message message) {
        Optional<MoIndicationMessage> optionalMoIndicationMessage = message.getBody(Optional.class);
        if (optionalMoIndicationMessage.isPresent()) {
            MoIndicationMessage moIndicationMessage = optionalMoIndicationMessage.get();
            Date startDate = moIndicationMessage.getStartDate().orElse(message.getHeader(VALIDITY_DATE, Date.class));
            MetadataBean.Builder metadataBeanBuilder = MetadataBean.builder()
                    .vocabularyId(MO_INDICATION)
                    .conceptCode(moIndicationMessage.getUcd7())
                    .startDate(startDate)
                    .standardConcept(1);
            moIndicationMessage.getEndDate().ifPresent(metadataBeanBuilder::endDate);
            return of(metadataContentBeanOptional(INSCRIPTION.representation(), moIndicationMessage.getRegistration()),
                    metadataContentBeanOptional(CLASS_IND_1.representation(), moIndicationMessage.getClassInd1()),
                    metadataContentBeanOptional(CLASS_IND_2.representation(), moIndicationMessage.getClassInd2()),
                    metadataContentBeanOptional(MoIndicationMetadataType.DCI.representation(), moIndicationMessage.getDci()),
                    metadataContentBeanOptional(MoIndicationMetadataType.GENER.representation(), moIndicationMessage.getGener()),
                    metadataContentBeanOptional(MoIndicationMetadataType.LABO.representation(), moIndicationMessage.getLab()),
                    metadataContentBeanOptional(MoIndicationMetadataType.LIB_INDICATION.representation(), moIndicationMessage.getLib()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(metadataContentBean -> MetadataMessage.of(metadataBeanBuilder.build(), metadataContentBean));
        } else {
            return Stream.empty();
        }
    }
}
