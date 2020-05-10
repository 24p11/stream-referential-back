package fr.aphp.referential.load.bean;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import fr.aphp.referential.load.annotation.Tupled;

@Tupled
@JsonSerialize(as = ImmutableMetadataContentBean.class)
public interface MetadataContentBean {
    static MetadataContentBean of(String name, String value) {
        return ImmutableMetadataContentBean.of(name, value);
    }

    String name();

    String value();

    // Others optional fields...
}
