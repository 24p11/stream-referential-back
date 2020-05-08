package fr.aphp.referential.load.processor.ccam.f001;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.camel.Message;

import fr.aphp.referential.load.bean.MetadataBean;
import fr.aphp.referential.load.message.ccam.f001.CcamMessage;

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
import static fr.aphp.referential.load.util.CamelUtils.VALIDITY_DATE;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

public class CcamMetadataProcessor {
    @SuppressWarnings("unchecked")
    public static Stream<MetadataBean> metadataBeanStream(Message message) {
        Optional<CcamMessage> ccamMessageOptional = message.getBody(Optional.class);
        if (ccamMessageOptional.isPresent()) {
            CcamMessage ccamMessage = ccamMessageOptional.get();
            return Stream.of(metadataBuilderOptional(EXTENSION_PMSI.representation(), ccamMessage.extensionPmsi()),
                    metadataBuilderOptional(CODE_PMSI.representation(), ccamMessage.codePmsi()),
                    metadataBuilderOptional(HAS.representation(), ccamMessage.compHas()),
                    metadataBuilderOptional(CONSIGN_PMSI.representation(), ccamMessage.consignPmsi()),
                    metadataBuilderOptional(MODIFICATION_TYPE.representation(), ccamMessage.modificationType()),
                    metadataBuilderOptional(MODIFICATION_VERSION.representation(), ccamMessage.modificationVersion()),
                    metadataBuilderOptional(RSC.representation(), ccamMessage.rsc()),
                    metadataBuilderOptional(AP.representation(), ccamMessage.ap()),
                    metadataBuilderOptional(ETM.representation(), ccamMessage.etm()),
                    metadataBuilderOptional(RGT.representation(), ccamMessage.rgt()),
                    metadataBuilderOptional(CLASSIFYING.representation(), ccamMessage.classifying()),
                    metadataBuilderOptional(BILLING_LIST.representation(), ccamMessage.billingList()),
                    metadataBuilderOptional(ICR.representation(), ccamMessage.icr()),
                    metadataBuilderOptional(ICR_PRIVATE.representation(), ccamMessage.icrPrivate()),
                    metadataBuilderOptional(ICR_A4.representation(), ccamMessage.icrA4()),
                    metadataBuilderOptional(ICR_ANAPATH.representation(), ccamMessage.icrAnapath()),
                    metadataBuilderOptional(ICR_REA.representation(), ccamMessage.icrRea()),
                    metadataBuilderOptional(MODIFIER.representation(), ccamMessage.modifier()),
                    metadataBuilderOptional(GEST_COMP.representation(), ccamMessage.gestComp()),
                    metadataBuilderOptional(GEST_COMP_ANES.representation(), ccamMessage.gestCompAnes()),
                    metadataBuilderOptional(DENOM.representation(), ccamMessage.denom()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .map(metadata -> metadata.conceptCode(ccamMessage.conceptCode()))
                    .map(metadata -> metadata.startDate(message.getHeader(VALIDITY_DATE, Date.class)))
                    .map(metadata -> metadata.standardConcept(1))
                    .map(MetadataBean.Builder::build);
        } else {
            return Stream.empty();
        }
    }

    private static Optional<MetadataBean.Builder> metadataBuilderOptional(String name, String value) {
        if (isNotBlank(value)) {
            return Optional.of(MetadataBean.builder()
                    .vocabularyId(CCAM)
                    .name(name)
                    .value(value));
        } else {
            return Optional.empty();
        }
    }
}
