package fr.aphp.referential.load.processor;

import fr.aphp.referential.load.bean.MetadataBean;
import fr.aphp.referential.load.bean.MetadataContentBean;
import fr.aphp.referential.load.bean.MetadataDictionaryBean;
import fr.aphp.referential.load.message.MetadataMessage;

import static org.apache.commons.lang3.StringUtils.EMPTY;

public class ToDbMetadataDictionaryProcessor {
    public static MetadataDictionaryBean metadataDictionaryBean(MetadataMessage metadataMessage) {
        MetadataBean metadataBean = metadataMessage.metadataBeanBuilder().content(EMPTY).build();
        MetadataContentBean metadataContentBean = metadataMessage.metadataContentBean();
        return MetadataDictionaryBean.of(metadataBean.vocabularyId(), metadataContentBean.name(), metadataBean.startDate());
    }
}
