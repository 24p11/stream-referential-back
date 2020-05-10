package fr.aphp.referential.load.processor;

import java.util.Optional;
import java.util.stream.Stream;

import org.apache.camel.Message;

import fr.aphp.referential.load.bean.MetadataContentBean;
import fr.aphp.referential.load.message.MetadataMessage;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

public interface MetadataProcessor {
    default Optional<MetadataContentBean> optionalMetadataContentBean(String name, String value) {
        return isNotBlank(value)
                ? Optional.of(MetadataContentBean.of(name, value))
                : Optional.empty();
    }

    Stream<Optional<MetadataMessage>> optionalMetadataMessageStream(Message message);
}
