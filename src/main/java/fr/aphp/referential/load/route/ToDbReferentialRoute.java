package fr.aphp.referential.load.route;

import org.apache.camel.processor.aggregate.GroupedBodyAggregationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mysql.cj.jdbc.exceptions.MySQLTransactionRollbackException;

import fr.aphp.referential.load.configuration.ApplicationConfiguration;
import fr.aphp.referential.load.processor.ToDbReferentialProcessor;

import static fr.aphp.referential.load.util.CamelUtils.TO_DB_REFERENTIAL_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.UPDATE_REFERENTIAL_BEAN;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.apache.camel.Exchange.FILE_NAME_ONLY;
import static org.apache.camel.Exchange.SPLIT_COMPLETE;

@Component
public class ToDbReferentialRoute extends BaseRoute {
    private static final Logger LOGGER = LoggerFactory.getLogger(ToDbReferentialRoute.class);
    private final ApplicationConfiguration applicationConfiguration;

    public ToDbReferentialRoute(ApplicationConfiguration applicationConfiguration) {
        this.applicationConfiguration = applicationConfiguration;

        setInput(direct(TO_DB_REFERENTIAL_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        exceptionHandler();

        from(getInput())
                .routeId(TO_DB_REFERENTIAL_ROUTE_ID)

                .aggregate(header(FILE_NAME_ONLY), new GroupedBodyAggregationStrategy())
                .completeAllOnStop()
                .eagerCheckCompletion()
                .completionSize(applicationConfiguration.getBatchSize())
                .completionTimeout(SECONDS.toMillis(5))
                .completionPredicate(exchangeProperty(SPLIT_COMPLETE))

                .process().message(ToDbReferentialProcessor::setHeaders)
                .to(mybatisUpdateEndDate())

                .to(mybatisBatchInsert("upsertReferential"));
    }

    private void exceptionHandler() {
        onException(MySQLTransactionRollbackException.class)
                // Attempt redelivery forever until it succeeds
                .maximumRedeliveries(-1)
                .delayPattern("0:3000;10:7000;20:30000")
                .onRedelivery(exchange -> {
                    LOGGER.warn("Retry SQL (deadlock detected)");
                });
    }

    private static String mybatisUpdateEndDate() {
        return mybatis("updateEndDateReferentialAfterLoad", "UpdateList", "inputHeader=" + UPDATE_REFERENTIAL_BEAN);
    }
}
