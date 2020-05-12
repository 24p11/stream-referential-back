package fr.aphp.referential.load.processor.ghmghs.f001;

import java.util.stream.Stream;

import org.apache.camel.Message;

import fr.aphp.referential.load.message.MetadataMessage;
import fr.aphp.referential.load.processor.MetadataProcessor;

public class GhmGhsMetadataProcessor implements MetadataProcessor {
    @Override
    public Stream<MetadataMessage> metadataMessageStream(Message message) {
        return null;
    }
}
