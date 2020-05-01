package fr.aphp.referential.load.route;

import java.io.File;
import java.util.Arrays;

import org.apache.camel.builder.EndpointConsumerBuilder;
import org.apache.camel.builder.EndpointProducerBuilder;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;

import fr.aphp.referential.load.domain.type.BaseType;

import static org.apache.ibatis.session.ExecutorType.BATCH;

public class BaseRoute extends EndpointRouteBuilder {
    private String input;
    private String output;

    @Override
    public void configure() throws Exception {
    }

    protected String getInput() {
        return input;
    }

    public BaseRoute setInput(String input) {
        this.input = input;

        return this;
    }

    public BaseRoute setInput(EndpointConsumerBuilder input) {
        this.input = input.getUri();

        return this;
    }

    protected String getOutput() {
        return output;
    }

    public BaseRoute setOutput(String output) {
        this.output = output;

        return this;
    }

    public BaseRoute setOutput(EndpointProducerBuilder output) {
        this.output = output.getUri();

        return this;
    }

    protected String directoryRouteId(String routeId, String inputDirectory) {
        return new StringBuilder(routeId)
                .append('-')
                .append(new File(inputDirectory).getName())
                .toString();
    }

    protected String direct(BaseType baseType) {
        return direct(baseType.name().toLowerCase()).getUri();
    }

    protected static String mybatis(String method, String statementType, String... params) {
        StringBuilder mybatis = new StringBuilder("mybatis:");

        mybatis.append(method);
        mybatis.append("?statementType=");
        mybatis.append(statementType);

        Arrays.asList(params)
                .forEach(param -> mybatis.append('&').append(param));

        return mybatis.toString();
    }

    protected static String mybatisBatchInsert(String method) {
        StringBuilder mybatis = new StringBuilder("mybatis:");

        mybatis.append(method);
        mybatis.append("?statementType=");
        mybatis.append("InsertList");
        mybatis.append("&executorType=");
        mybatis.append(BATCH);

        return mybatis.toString();
    }
}
