package fr.aphp.referential.load.bean;

import java.util.Date;

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

    Date startDate();

    class Builder extends ImmutableReferentialBean.Builder {}
}
