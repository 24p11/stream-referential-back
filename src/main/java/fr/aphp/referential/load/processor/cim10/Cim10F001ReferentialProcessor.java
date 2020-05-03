package fr.aphp.referential.load.processor.cim10;

import java.util.Date;

import org.apache.camel.Message;

import fr.aphp.referential.load.bean.ReferentialBean;
import fr.aphp.referential.load.message.cim10.Cim10F001Message;

import static fr.aphp.referential.load.domain.type.SourceType.CIM10;
import static fr.aphp.referential.load.util.CamelUtils.VALIDITY_DATE;

public class Cim10F001ReferentialProcessor {
    public static ReferentialBean referentialBean(Message message) {
        Cim10F001Message cim10F001Message = message.getBody(Cim10F001Message.class);
        return ReferentialBean.builder()
                .type(CIM10)
                .domainId(cim10F001Message.getDomainId())
                .label(cim10F001Message.getLabel())
                .startDate(message.getHeader(VALIDITY_DATE, Date.class))
                .build();
    }
}
