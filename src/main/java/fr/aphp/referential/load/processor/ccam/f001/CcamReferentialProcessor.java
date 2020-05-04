package fr.aphp.referential.load.processor.ccam.f001;

import java.util.Optional;

import org.apache.camel.Message;

import fr.aphp.referential.load.bean.ReferentialBean;
import fr.aphp.referential.load.message.ccam.f001.CcamMessage;

public class CcamReferentialProcessor {
    @SuppressWarnings("unchecked")
    public static Optional<ReferentialBean> optionalReferentialBean(Message message) {
        Optional<CcamMessage> ccamMessageOptional = message.getBody(Optional.class);
        return ccamMessageOptional.map(CcamReferentialProcessor::referentialBean);
    }

    private static ReferentialBean referentialBean(CcamMessage ccamMessage) {
        return ReferentialBean.builder()
                .type(ccamMessage.type())
                .domainId(ccamMessage.domainId())
                .label(ccamMessage.label())
                .startDate(ccamMessage.startDate())
                .build();
    }
}
