package fr.aphp.referential.load.processor;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.aphp.referential.load.bean.MetadataBean;
import fr.aphp.referential.load.message.MetadataMessage;
import io.vavr.control.Try;

@Component
public class ToDbMetadataProcessor {
    private final ObjectMapper objectMapper;

    public ToDbMetadataProcessor(ObjectMapper objectMapper) {this.objectMapper = objectMapper;}

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public Optional<MetadataBean> metadataBean(Optional<MetadataMessage> optionalMetadataMessage) {
        if (optionalMetadataMessage.isPresent()) {
            MetadataMessage metadataMessage = optionalMetadataMessage.get();
            MetadataBean metadataBean = metadataMessage.metadataBeanBuilder()
                    .content(Try.of(() -> objectMapper.writeValueAsString(metadataMessage.metadataContentBean())).get())
                    .build();
            return Optional.of(metadataBean);
        } else {
            return Optional.empty();
        }

    }
}
