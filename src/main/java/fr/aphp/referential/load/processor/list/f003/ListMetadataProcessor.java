package fr.aphp.referential.load.processor.list.f003;

import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.camel.Message;
import org.springframework.stereotype.Component;

import fr.aphp.referential.load.bean.MetadataBean;
import fr.aphp.referential.load.bean.MetadataContentBean;
import fr.aphp.referential.load.domain.type.SourceType;
import fr.aphp.referential.load.domain.type.list.f001.ListMetadataType;
import fr.aphp.referential.load.message.MetadataMessage;
import fr.aphp.referential.load.message.list.f003.ListMessage;
import fr.aphp.referential.load.processor.MetadataProcessor;

import static fr.aphp.referential.load.domain.type.list.f003.ListMetadataType.DEVICE;
import static fr.aphp.referential.load.domain.type.list.f003.ListMetadataType.END_DATE;
import static fr.aphp.referential.load.domain.type.list.f003.ListMetadataType.LIST;
import static fr.aphp.referential.load.domain.type.list.f003.ListMetadataType.NAME;
import static fr.aphp.referential.load.domain.type.list.f003.ListMetadataType.ORGAN;
import static fr.aphp.referential.load.domain.type.list.f003.ListMetadataType.START_DATE;
import static fr.aphp.referential.load.domain.type.list.f003.ListMetadataType.VOCABULARY;
import static fr.aphp.referential.load.util.CamelUtils.LIST_F003_METADATA_PROCESSOR;
import static java.util.stream.Stream.of;

@Component(LIST_F003_METADATA_PROCESSOR)
public class ListMetadataProcessor implements MetadataProcessor {
    @SuppressWarnings("unchecked")
    @Override
    public Stream<MetadataMessage> metadataMessageStream(Message message) {
        var listMessage = message.getBody(ListMessage.class);
        var vocabularyId = message.getHeader(VOCABULARY.name(), SourceType.class);
        var startDate = message.getHeader(START_DATE.name(), Date.class);
        var metadataBeanBuilder = MetadataBean.builder()
                .vocabularyId(vocabularyId)
                .conceptCode(listMessage.getCode())
                .startDate(startDate)
                .standardConcept(0);
        Optional<Date> endDateOptional = message.getHeader(END_DATE.name(), Optional.class);
        endDateOptional.ifPresent(metadataBeanBuilder::endDate);

        var any = new HashMap<String, Object>();
        any.put(ListMetadataType.AUTHOR.representation(), message.getHeader(ListMetadataType.AUTHOR.name(), String.class));
        any.put(ListMetadataType.VERSION.representation(), message.getHeader(ListMetadataType.VERSION.name(), String.class));
        any.put(DEVICE.representation(), listMessage.getDevice());
        any.put(ORGAN.representation(), listMessage.getOrgan());
        MetadataContentBean metadataContentBean = MetadataContentBean.builder()
                .name(LIST.name())
                .value(message.getHeader(NAME.name(), String.class))
                .any(any)
                .build();
        return of(metadataContentBean).map(mb -> MetadataMessage.of(metadataBeanBuilder.build(), metadataContentBean));
    }
}
