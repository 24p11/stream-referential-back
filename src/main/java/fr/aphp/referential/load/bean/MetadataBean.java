package fr.aphp.referential.load.bean;

import java.util.Date;

import javax.annotation.Nullable;

import org.immutables.value.Value.Derived;

import fr.aphp.referential.load.annotation.Builded;
import fr.aphp.referential.load.domain.type.SourceType;

import static fr.aphp.referential.load.util.KeyUtils.key;

@Builded
public interface MetadataBean {
    static Builder builder() {
        return new Builder();
    }

    SourceType vocabularyId();

    String conceptCode();

    @Derived
    default String conceptId() {
        return key(vocabularyId(), conceptCode());
    }

    @Nullable
    String content();

    default int standardConcept() {
        return 0;
    }

    Date startDate();

    @Nullable
    Date endDate();

    class Builder extends ImmutableMetadataBean.Builder {}
}
