package fr.aphp.referential.load.processor.cim10;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.camel.Message;

import fr.aphp.referential.load.bean.MetadataBean;
import fr.aphp.referential.load.message.cim10.Cim10F001Message;

import static fr.aphp.referential.load.domain.type.SourceType.CIM10;
import static fr.aphp.referential.load.util.CamelUtils.VALIDITY_DATE;

public class Cim10F001MetadataProcessor {
    public static Collection<MetadataBean> metadataBean(Message message) {
        Cim10F001Message cim10F001Message = message.getBody(Cim10F001Message.class);
        return Stream.of(metadataBuilder("mco", cim10F001Message.getSsr()), metadataBuilder("psy", cim10F001Message.getPsy()))
                .map(metadata -> metadata.startDate(message.getHeader(VALIDITY_DATE, Date.class)))
                .map(metadata -> metadata.domainId(cim10F001Message.getDomainId()))
                .map(MetadataBean.Builder::build)
                .collect(Collectors.toList());
    }

    private static MetadataBean.Builder metadataBuilder(String entry, String value) {
        return MetadataBean.builder()
                .type(CIM10)
                .entry(entry)
                .value(value);
    }
}
