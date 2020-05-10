package fr.aphp.referential.load.processor;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.aphp.referential.load.bean.MetadataBean;
import fr.aphp.referential.load.message.MetadataMessage;
import io.vavr.control.Try;

@Component
public class ToDbMetadataProcessor {
    private final ObjectMapper objectMapper;

    public ToDbMetadataProcessor(ObjectMapper objectMapper) {this.objectMapper = objectMapper;}

    public MetadataBean metadataBean(MetadataMessage metadataMessage) {
        return metadataMessage.metadataBeanBuilder()
                .content(Try.of(() -> objectMapper.writeValueAsString(metadataMessage.metadataContentBean())).get())
                .build();
    }
}
