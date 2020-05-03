package fr.aphp.referential.load.bean;

import org.immutables.value.Value.Derived;

import fr.aphp.referential.load.annotation.Tupled;
import fr.aphp.referential.load.domain.type.SourceType;

import static fr.aphp.referential.load.util.CamelUtils.DISABLE_END_DATE;

@Tupled
public interface UpdateReferentialBean {
    static UpdateReferentialBean of(SourceType sourceType) {
        return ImmutableUpdateReferentialBean.of(sourceType, DISABLE_END_DATE);
    }

    static UpdateReferentialBean of(SourceType sourceType, String endDate) {
        return ImmutableUpdateReferentialBean.of(sourceType, endDate);
    }

    SourceType sourceType();

    String endDate();

    @Derived
    default String endDateMarker() {
        return DISABLE_END_DATE;
    }
}
