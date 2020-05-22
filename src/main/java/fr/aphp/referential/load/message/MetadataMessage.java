package fr.aphp.referential.load.message;

import fr.aphp.referential.load.annotation.Tupled;
import fr.aphp.referential.load.bean.MetadataBean;
import fr.aphp.referential.load.bean.MetadataContentBean;

@Tupled
public interface MetadataMessage extends Message {
    static MetadataMessage of(MetadataBean metadataBean, MetadataContentBean metadataContentBean) {
        return ImmutableMetadataMessage.of(metadataBean, metadataContentBean);
    }

    MetadataBean metadataBean();

    MetadataContentBean metadataContentBean();
}
