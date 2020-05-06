package fr.aphp.referential.load.processor.cim10.f001;

import java.util.Date;
import java.util.stream.Stream;

import org.apache.camel.Message;

import fr.aphp.referential.load.bean.MetadataBean;
import fr.aphp.referential.load.message.cim10.f001.Cim10Message;

import static fr.aphp.referential.load.domain.type.SourceType.CIM10;
import static fr.aphp.referential.load.domain.type.cim10.f001.Cim10MetadataType.MCO_HAD;
import static fr.aphp.referential.load.domain.type.cim10.f001.Cim10MetadataType.PSY;
import static fr.aphp.referential.load.domain.type.cim10.f001.Cim10MetadataType.SSR;
import static fr.aphp.referential.load.util.CamelUtils.VALIDITY_DATE;

public class Cim10MetadataProcessor {
    public static Stream<MetadataBean> metadataBeanStream(Message message) {
        if (message.getBody() instanceof Cim10Message) {
            Cim10Message cim10F001Message = message.getBody(Cim10Message.class);
            MetadataBean.Builder mcoHad = metadataBuilder(MCO_HAD.representation(), cim10F001Message.getMcoHad());
            MetadataBean.Builder ssr = metadataBuilder(SSR.representation(), cim10F001Message.getSsr());
            MetadataBean.Builder psy = metadataBuilder(PSY.representation(), cim10F001Message.getPsy());
            return Stream.of(mcoHad, ssr, psy)
                    .map(metadata -> metadata.startDate(message.getHeader(VALIDITY_DATE, Date.class)))
                    .map(metadata -> metadata.conceptCode(cim10F001Message.getConceptCode()))
                    .map(MetadataBean.Builder::build);
        } else {
            return Stream.empty();
        }
    }

    private static MetadataBean.Builder metadataBuilder(String name, String value) {
        return MetadataBean.builder()
                .vocabularyId(CIM10)
                .name(name)
                .value(value);
    }
}
