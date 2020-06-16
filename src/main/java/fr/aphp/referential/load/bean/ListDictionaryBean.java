package fr.aphp.referential.load.bean;

import java.util.Date;

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

    Date startDate();

    class Builder extends ImmutableListDictionaryBean.Builder {}
}
