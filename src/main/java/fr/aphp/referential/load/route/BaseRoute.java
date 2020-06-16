package fr.aphp.referential.load.route;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.cj.jdbc.exceptions.MySQLTransactionRollbackException;

import fr.aphp.referential.load.domain.type.BaseType;

import static fr.aphp.referential.load.util.CamelUtils.FORMAT;
import static fr.aphp.referential.load.util.CamelUtils.LIST_METADATA_ROUTE_REGEX;
import static fr.aphp.referential.load.util.CamelUtils.TO_DB_LIST_DICTIONARY_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.TO_DB_METADATA_ROUTE_ID;
import static fr.aphp.referential.load.util.KeyUtils.ROUTE_ID_DELIMITER;
import static org.apache.camel.Exchange.RECIPIENT_LIST_ENDPOINT;
import static org.apache.camel.Exchange.SPLIT_COMPLETE;
import static org.apache.camel.support.builder.PredicateBuilder.and;
import static org.apache.ibatis.session.ExecutorType.BATCH;

public class BaseRoute extends RouteBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseRoute.class);

    private String input;
    private String output;
    private String[] outputs;

    @Override
    public void configure() throws Exception {
        interceptSendToEndpoint(direct(TO_DB_METADATA_ROUTE_ID))
                .filter(and(exchangeProperty(RECIPIENT_LIST_ENDPOINT).regex(LIST_METADATA_ROUTE_REGEX), exchangeProperty(SPLIT_COMPLETE)))
                // Because Stream can be processed once only
                .transform().body(Stream.class, this::collection)
                .wireTap(direct(TO_DB_LIST_DICTIONARY_ROUTE_ID));
    }

    private Collection<?> collection(Stream<?> stream) {
        return stream.collect(Collectors.toList());
    }

    protected String getInput() {
        return input;
    }

    public BaseRoute setInput(String input) {
        this.input = input;

        return this;
    }

    public BaseRoute setInput(StringBuilder input) {
        this.input = input.toString();

        return this;
    }

    protected String getOutput() {
        return output;
    }

    public BaseRoute setOutput(String output) {
        this.output = output;

        return this;
    }

    public BaseRoute setOutput(StringBuilder output) {
        this.output = output.toString();

        return this;
    }

    public String[] getOutputs() {
        return outputs;
    }

    public BaseRoute setOutputs(String... outputs) {
        this.outputs = outputs;

        return this;
    }

    protected String directoryRouteId(String routeId, String inputDirectory) {
        return new StringBuilder(routeId)
                .append('-')
                .append(new File(inputDirectory).getName())
                .toString();
    }

    protected String direct(String destination) {
        return "direct:" + destination;
    }

    protected String directUnchecked(String destination) {
        return new StringBuilder(direct(destination))
                .append("?block=")
                .append(false)
                .append("&failIfNoConsumers=")
                .append(false)
                .toString();
    }

    protected String direct(BaseType baseType) {
        return direct(baseType.name().toLowerCase());
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
        return header(FORMAT).isEqualTo(type);
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
                .map(id -> id.replace("_", ROUTE_ID_DELIMITER))
                .collect(Collectors.joining(ROUTE_ID_DELIMITER));
        return String.format("%s-%s-route", identifiers, identifier);
    }
}
