package fr.aphp.referential.load.bean;

import java.util.Date;

import fr.aphp.referential.load.annotation.Builded;
import fr.aphp.referential.load.domain.type.SourceType;

@Builded
public interface ConceptBean {
    static Builder builder() {
        return new Builder();
    }

    SourceType vocabularyId();

    String conceptCode();

    String conceptName();

    Date startDate();

    default int standardConcept() {
        return 0;
    }

    class Builder extends ImmutableConceptBean.Builder {}
}
