package fr.aphp.referential.load.bean;

import org.immutables.value.Value.Derived;

import fr.aphp.referential.load.annotation.Tupled;
import fr.aphp.referential.load.domain.type.SourceType;

import static fr.aphp.referential.load.util.CamelUtils.DISABLE_END_DATE;

@Tupled
public interface UpdateConceptBean {
    static UpdateConceptBean of(SourceType vocabularyId) {
        return ImmutableUpdateConceptBean.of(vocabularyId, DISABLE_END_DATE);
    }

    static UpdateConceptBean of(SourceType vocabularyId, String endDate) {
        return ImmutableUpdateConceptBean.of(vocabularyId, endDate);
    }

    SourceType vocabularyId();

    String endDate();

    @Derived
    default String endDateMarker() {
        return DISABLE_END_DATE;
    }
}
