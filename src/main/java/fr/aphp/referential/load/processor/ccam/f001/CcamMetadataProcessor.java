package fr.aphp.referential.load.processor.ccam.f001;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.camel.Message;
import org.springframework.stereotype.Component;

import fr.aphp.referential.load.bean.MetadataBean;
import fr.aphp.referential.load.message.MetadataMessage;
import fr.aphp.referential.load.message.ccam.f001.CcamMessage;
import fr.aphp.referential.load.processor.MetadataProcessor;

import static fr.aphp.referential.load.domain.type.SourceType.CCAM;
import static fr.aphp.referential.load.domain.type.ccam.f001.CcamMetadataType.AP;
import static fr.aphp.referential.load.domain.type.ccam.f001.CcamMetadataType.BILLING_LIST;
import static fr.aphp.referential.load.domain.type.ccam.f001.CcamMetadataType.CLASSIFYING;
import static fr.aphp.referential.load.domain.type.ccam.f001.CcamMetadataType.CODE_PMSI;
import static fr.aphp.referential.load.domain.type.ccam.f001.CcamMetadataType.CONSIGN_PMSI;
import static fr.aphp.referential.load.domain.type.ccam.f001.CcamMetadataType.DENOM;
import static fr.aphp.referential.load.domain.type.ccam.f001.CcamMetadataType.ETM;
import static fr.aphp.referential.load.domain.type.ccam.f001.CcamMetadataType.EXTENSION_PMSI;
import static fr.aphp.referential.load.domain.type.ccam.f001.CcamMetadataType.GEST_COMP;
import static fr.aphp.referential.load.domain.type.ccam.f001.CcamMetadataType.GEST_COMP_ANES;
import static fr.aphp.referential.load.domain.type.ccam.f001.CcamMetadataType.HAS;
import static fr.aphp.referential.load.domain.type.ccam.f001.CcamMetadataType.ICR;
import static fr.aphp.referential.load.domain.type.ccam.f001.CcamMetadataType.ICR_A4;
import static fr.aphp.referential.load.domain.type.ccam.f001.CcamMetadataType.ICR_ANAPATH;
import static fr.aphp.referential.load.domain.type.ccam.f001.CcamMetadataType.ICR_PRIVATE;
import static fr.aphp.referential.load.domain.type.ccam.f001.CcamMetadataType.ICR_REA;
import static fr.aphp.referential.load.domain.type.ccam.f001.CcamMetadataType.MODIFICATION_TYPE;
import static fr.aphp.referential.load.domain.type.ccam.f001.CcamMetadataType.MODIFICATION_VERSION;
import static fr.aphp.referential.load.domain.type.ccam.f001.CcamMetadataType.MODIFIER;
import static fr.aphp.referential.load.domain.type.ccam.f001.CcamMetadataType.RGT;
import static fr.aphp.referential.load.domain.type.ccam.f001.CcamMetadataType.RSC;
import static fr.aphp.referential.load.util.CamelUtils.CCAM_F001_METADATA_PROCESSOR;
import static fr.aphp.referential.load.util.CamelUtils.VALIDITY_DATE;
import static java.util.stream.Stream.empty;
import static java.util.stream.Stream.of;

@Component(CCAM_F001_METADATA_PROCESSOR)
public class CcamMetadataProcessor implements MetadataProcessor {
    @Override
    @SuppressWarnings("unchecked")
    public Stream<MetadataMessage> metadataMessageStream(Message message) {
        Optional<CcamMessage> ccamMessageOptional = message.getBody(Optional.class);
        if (ccamMessageOptional.isPresent()) {
            CcamMessage ccamMessage = ccamMessageOptional.get();
            MetadataBean metadataBean = MetadataBean.builder()
                    .vocabularyId(CCAM)
                    .conceptCode(ccamMessage.conceptCode())
                    .startDate(message.getHeader(VALIDITY_DATE, Date.class))
                    .standardConcept(1)
                    .build();
            return of(metadataContentBeanOptional(EXTENSION_PMSI.representation(), ccamMessage.extensionPmsi()),
                    metadataContentBeanOptional(CODE_PMSI.representation(), ccamMessage.codePmsi()),
                    metadataContentBeanOptional(HAS.representation(), ccamMessage.compHas()),
                    metadataContentBeanOptional(CONSIGN_PMSI.representation(), ccamMessage.consignPmsi()),
                    metadataContentBeanOptional(MODIFICATION_TYPE.representation(), ccamMessage.modificationType()),
                    metadataContentBeanOptional(MODIFICATION_VERSION.representation(), ccamMessage.modificationVersion()),
                    metadataContentBeanOptional(RSC.representation(), ccamMessage.rsc()),
                    metadataContentBeanOptional(AP.representation(), ccamMessage.ap()),
                    metadataContentBeanOptional(ETM.representation(), ccamMessage.etm()),
                    metadataContentBeanOptional(RGT.representation(), ccamMessage.rgt()),
                    metadataContentBeanOptional(CLASSIFYING.representation(), ccamMessage.classifying()),
                    metadataContentBeanOptional(BILLING_LIST.representation(), ccamMessage.billingList()),
                    metadataContentBeanOptional(ICR.representation(), ccamMessage.icr()),
                    metadataContentBeanOptional(ICR_PRIVATE.representation(), ccamMessage.icrPrivate()),
                    metadataContentBeanOptional(ICR_A4.representation(), ccamMessage.icrA4()),
                    metadataContentBeanOptional(ICR_ANAPATH.representation(), ccamMessage.icrAnapath()),
                    metadataContentBeanOptional(ICR_REA.representation(), ccamMessage.icrRea()),
                    metadataContentBeanOptional(MODIFIER.representation(), ccamMessage.modifier()),
                    metadataContentBeanOptional(GEST_COMP.representation(), ccamMessage.gestComp()),
                    metadataContentBeanOptional(GEST_COMP_ANES.representation(), ccamMessage.gestCompAnes()),
                    metadataContentBeanOptional(DENOM.representation(), ccamMessage.denom()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(metadataContentBean -> MetadataMessage.of(metadataBean, metadataContentBean));
        } else {
            return empty();
        }
    }
}
