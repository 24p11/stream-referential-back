package fr.aphp.referential.load.bean;

import org.immutables.value.Value.Derived;

import fr.aphp.referential.load.annotation.Tupled;
import fr.aphp.referential.load.domain.type.SourceType;

import static fr.aphp.referential.load.util.CamelUtils.DISABLE_END_DATE;

@Tupled
public interface UpdateConceptBean {
    static UpdateConceptBean of(SourceType sourceType) {
        return ImmutableUpdateConceptBean.of(sourceType, DISABLE_END_DATE);
    }

    static UpdateConceptBean of(SourceType sourceType, String endDate) {
        return ImmutableUpdateConceptBean.of(sourceType, endDate);
    }

    SourceType sourceType();

    String endDate();

    @Derived
    default String endDateMarker() {
        return DISABLE_END_DATE;
    }
}
