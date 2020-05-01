package fr.aphp.referential.load.bean;

import org.immutables.value.Value.Derived;

import fr.aphp.referential.load.annotation.Tupled;
import fr.aphp.referential.load.domain.type.SourceType;

import static fr.aphp.referential.load.util.CamelUtils.DISABLE_END_DATE;

@Tupled
public interface UpdateReferentialBean {
    static UpdateReferentialBean of(SourceType sourceType) {
        return ImmutableUpdateReferentialBean.of(sourceType);
    }

    SourceType sourceType();

    @Derived
    default String endDate() {
        return DISABLE_END_DATE;
    }
}
