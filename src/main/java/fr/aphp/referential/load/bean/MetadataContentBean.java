package fr.aphp.referential.load.bean;

import java.util.Collections;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import fr.aphp.referential.load.annotation.Tupled;

@Tupled
@JsonSerialize(as = ImmutableMetadataContentBean.class)
public interface MetadataContentBean {
    static MetadataContentBean of(String name, String value) {
        return ImmutableMetadataContentBean.of(name, value, Collections.emptyMap());
    }

    static Builder builder() {
        return new Builder();
    }

    String name();

    String value();

    @JsonAnyGetter
    Map<String, Object> any();

    class Builder extends ImmutableMetadataContentBean.Builder {}
}
