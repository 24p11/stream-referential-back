package fr.aphp.referential.load.bean;

import java.util.Optional;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import fr.aphp.referential.load.annotation.Tupled;

@Tupled
@JsonSerialize(as = ImmutableMetadataContentBean.class)
public interface MetadataContentBean {
    static MetadataContentBean of(String name, String value) {
        return ImmutableMetadataContentBean.of(name, value, Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
    }

    static Builder builder() {
        return new Builder();
    }

    String name();

    String value();

    Optional<String> author();

    Optional<String> version();

    Optional<String> codeLabel();

    Optional<String> codes();

    class Builder extends ImmutableMetadataContentBean.Builder {}
}
