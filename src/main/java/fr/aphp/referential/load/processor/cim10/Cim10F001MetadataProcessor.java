package fr.aphp.referential.load.processor.cim10;

import java.util.Date;
import java.util.stream.Stream;

import org.apache.camel.Message;

import fr.aphp.referential.load.bean.MetadataBean;
import fr.aphp.referential.load.message.cim10.Cim10F001Message;

import static fr.aphp.referential.load.domain.type.SourceType.CIM10;
import static fr.aphp.referential.load.domain.type.cim10.Cim10F001MetadataType.MCO_HAD;
import static fr.aphp.referential.load.domain.type.cim10.Cim10F001MetadataType.PSY;
import static fr.aphp.referential.load.domain.type.cim10.Cim10F001MetadataType.SSR;
import static fr.aphp.referential.load.util.CamelUtils.VALIDITY_DATE;

public class Cim10F001MetadataProcessor {
    public static Stream<MetadataBean> metadataBeanStream(Message message) {
        if (message.getBody() instanceof Cim10F001Message) {
            Cim10F001Message cim10F001Message = message.getBody(Cim10F001Message.class);
            return Stream.of(
                    metadataBuilder(MCO_HAD.representation(), cim10F001Message.getMcoHad()),
                    metadataBuilder(SSR.representation(), cim10F001Message.getSsr()),
                    metadataBuilder(PSY.representation(), cim10F001Message.getPsy()))
                    .map(metadata -> metadata.startDate(message.getHeader(VALIDITY_DATE, Date.class)))
                    .map(metadata -> metadata.domainId(cim10F001Message.getDomainId()))
                    .map(MetadataBean.Builder::build);
        } else {
            return Stream.empty();
        }
    }

    private static MetadataBean.Builder metadataBuilder(String entry, String value) {
        return MetadataBean.builder()
                .type(CIM10)
                .entry(entry)
                .value(value);
    }
}
