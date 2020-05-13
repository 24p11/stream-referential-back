package fr.aphp.referential.load.bean;

import java.util.Date;

import fr.aphp.referential.load.annotation.Tupled;
import fr.aphp.referential.load.domain.type.SourceType;

@Tupled
public interface MetadataDictionaryBean {
    static MetadataDictionaryBean of(SourceType vocabularyId, String metadataName, Date startDate) {
        return ImmutableMetadataDictionaryBean.of(vocabularyId, metadataName, startDate);
    }

    SourceType vocabularyId();

    String metadataName();

    Date startDate();
}
