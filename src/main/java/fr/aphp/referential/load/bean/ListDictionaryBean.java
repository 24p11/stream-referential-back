package fr.aphp.referential.load.bean;

import fr.aphp.referential.load.annotation.Builded;
import fr.aphp.referential.load.domain.type.SourceType;

@Builded
public interface ListDictionaryBean {
    static Builder builder() {
        return new Builder();
    }

    SourceType vocabularyId();

    String name();

    String version();

    String author();

    class Builder extends ImmutableListDictionaryBean.Builder {}
}
