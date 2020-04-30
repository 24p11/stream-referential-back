package fr.aphp.referential.load.route;

import java.io.File;

import org.apache.camel.builder.EndpointConsumerBuilder;
import org.apache.camel.builder.EndpointProducerBuilder;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;

import fr.aphp.referential.load.domain.type.SourceType;

public class BaseRoute extends EndpointRouteBuilder {
    private String input;
    private String output;

    @Override
    public void configure() throws Exception {
    }

    protected String getInput() {
        return input;
    }

    protected BaseRoute setInput(String input) {
        this.input = input;

        return this;
    }

    protected BaseRoute setInput(EndpointConsumerBuilder input) {
        this.input = input.getUri();

        return this;
    }

    protected String getOutput() {
        return output;
    }

    protected BaseRoute setOutput(String output) {
        this.output = output;

        return this;
    }

    protected BaseRoute setOutput(EndpointProducerBuilder output) {
        this.output = output.getUri();

        return this;
    }

    protected String directoryRouteId(String routeId, String inputDirectory) {
        return new StringBuilder(routeId)
                .append('-')
                .append(new File(inputDirectory).getName())
                .toString();
    }

    protected String route(SourceType sourceType) {
        return direct(sourceType.name().toLowerCase()).getUri();
    }
}
