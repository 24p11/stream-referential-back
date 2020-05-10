package fr.aphp.referential.load.configuration;

import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.spi.DataFormatFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.databind.PropertyNamingStrategy.SNAKE_CASE;

@Configuration
public class JacksonConfiguration {
    @Bean("json-jackson")
    public DataFormatFactory dataFormatFactoryJson() {
        return this::jacksonDataFormat;
    }

    private JacksonDataFormat jacksonDataFormat() {
        JacksonDataFormat jacksonDataFormat = new JacksonDataFormat();
        jacksonDataFormat.setObjectMapper(objectMapper());

        return jacksonDataFormat;
    }

    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        configure(mapper).setPropertyNamingStrategy(SNAKE_CASE);

        return mapper;
    }

    private ObjectMapper configure(ObjectMapper mapper) {
        return mapper
                .setSerializationInclusion(NON_NULL)
                .setSerializationInclusion(NON_EMPTY)
                .setPropertyNamingStrategy(SNAKE_CASE)
                .enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS)
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule())
                .registerModule(new GuavaModule());
    }

}
