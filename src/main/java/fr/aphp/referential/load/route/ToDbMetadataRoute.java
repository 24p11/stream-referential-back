package fr.aphp.referential.load.route;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import fr.aphp.referential.load.aggregator.ListAggregator;
import fr.aphp.referential.load.configuration.ApplicationConfiguration;

import static fr.aphp.referential.load.util.CamelUtils.TO_DB_METADATA_ROUTE_ID;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.apache.camel.Exchange.FILE_NAME_ONLY;
import static org.apache.camel.Exchange.SPLIT_COMPLETE;

@Component
public class ToDbMetadataRoute extends BaseRoute {
    private static final Logger LOGGER = LoggerFactory.getLogger(ToDbMetadataRoute.class);
    private final ApplicationConfiguration applicationConfiguration;

    public ToDbMetadataRoute(ApplicationConfiguration applicationConfiguration) {
        this.applicationConfiguration = applicationConfiguration;

        setInput(direct(TO_DB_METADATA_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        mysqlDeadlockExceptionHandler();

        from(getInput())
                .routeId(TO_DB_METADATA_ROUTE_ID)

                .aggregate(header(FILE_NAME_ONLY), new ListAggregator())
                .completeAllOnStop()
                .eagerCheckCompletion()
                .completionSize(applicationConfiguration.getBatchSize())
                .completionTimeout(SECONDS.toMillis(5))
                .completionPredicate(exchangeProperty(SPLIT_COMPLETE))

                .to(mybatisBatchInsert("upsertMetadata"));
    }

}
