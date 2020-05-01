package fr.aphp.referential.load.bean;

import fr.aphp.referential.load.annotation.Builded;
import fr.aphp.referential.load.domain.type.SourceType;

@Builded
public interface ReferentialBean {
    static Builder builder() {
        return new Builder();
    }

    SourceType type();

    String domainId();

    String label();

    class Builder extends ImmutableReferentialBean.Builder {}
}
