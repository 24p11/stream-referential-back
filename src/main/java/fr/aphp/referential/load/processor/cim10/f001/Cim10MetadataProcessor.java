package fr.aphp.referential.load.processor.cim10.f001;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.camel.Message;
import org.springframework.stereotype.Component;

import fr.aphp.referential.load.bean.MetadataBean;
import fr.aphp.referential.load.bean.MetadataContentBean;
import fr.aphp.referential.load.message.MetadataMessage;
import fr.aphp.referential.load.message.cim10.f001.Cim10Message;
import fr.aphp.referential.load.processor.MetadataProcessor;

import static fr.aphp.referential.load.domain.type.SourceType.CIM10;
import static fr.aphp.referential.load.domain.type.cim10.f001.Cim10MetadataType.MCO_HAD;
import static fr.aphp.referential.load.domain.type.cim10.f001.Cim10MetadataType.PSY;
import static fr.aphp.referential.load.domain.type.cim10.f001.Cim10MetadataType.SSR;
import static fr.aphp.referential.load.util.CamelUtils.CIM10_F001_METADATA_PROCESSOR;
import static fr.aphp.referential.load.util.CamelUtils.VALIDITY_DATE;

@Component(CIM10_F001_METADATA_PROCESSOR)
public class Cim10MetadataProcessor implements MetadataProcessor {
    @Override
    public Stream<MetadataMessage> metadataMessageStream(Message message) {
        if (message.getBody() instanceof Cim10Message) {
            Cim10Message cim10F001Message = message.getBody(Cim10Message.class);
            MetadataBean.Builder MetadataBeanBuilder = MetadataBean.builder()
                    .vocabularyId(CIM10)
                    .conceptCode(cim10F001Message.getConceptCode())
                    .startDate(message.getHeader(VALIDITY_DATE, Date.class))
                    .standardConcept(1);
            Optional<MetadataContentBean> mcoHad = metadataContentBeanOptional(MCO_HAD.representation(), cim10F001Message.getMcoHad());
            Optional<MetadataContentBean> ssr = metadataContentBeanOptional(SSR.representation(), cim10F001Message.getSsr());
            Optional<MetadataContentBean> psy = metadataContentBeanOptional(PSY.representation(), cim10F001Message.getPsy());
            return Stream.of(mcoHad, ssr, psy)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(metadataContentBean -> MetadataMessage.of(MetadataBeanBuilder, metadataContentBean));
        } else {
            return Stream.empty();
        }
    }
}
