package fr.aphp.referential.load.route;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.camel.Predicate;
import org.apache.camel.builder.EndpointConsumerBuilder;
import org.apache.camel.builder.EndpointProducerBuilder;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.cj.jdbc.exceptions.MySQLTransactionRollbackException;

import fr.aphp.referential.load.domain.type.BaseType;

import static fr.aphp.referential.load.util.KeyUtils.ROUTE_ID_DELIMITER;
import static java.lang.String.format;
import static org.apache.ibatis.session.ExecutorType.BATCH;

public class BaseRoute extends EndpointRouteBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseRoute.class);

    private String input;
    private String output;
    private String[] outputs;

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

    public String[] getOutputs() {
        return outputs;
    }

    public BaseRoute setOutputs(String... outputs) {
        this.outputs = outputs;

        return this;
    }

    public void setOutputs(EndpointProducerBuilder... endpointProducerBuilders) {
        this.outputs = Arrays.stream(endpointProducerBuilders)
                .map(EndpointProducerBuilder::getUri)
                .toArray(String[]::new);
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

    protected Predicate isFormat(BaseType type) {
        return simple(format("${file:name.ext.single} ~~ '%s'", type));
    }

    protected void mysqlDeadlockExceptionHandler() {
        onException(MySQLTransactionRollbackException.class)
                // Attempt redelivery forever until it succeeds
                .maximumRedeliveries(-1)
                .delayPattern("0:3000;10:7000;20:30000")
                .onRedelivery(exchange -> {
                    LOGGER.warn("Retry SQL (deadlock detected)");
                });
    }

    protected static String dynamicRouteId(String identifier, String... ids) {
        String identifiers = Arrays.stream(ids)
                .map(String::toLowerCase)
                .collect(Collectors.joining(ROUTE_ID_DELIMITER));
        return String.format("%s-%s-route", identifiers, identifier);
    }
}
