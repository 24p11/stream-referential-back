package fr.aphp.referential.load.route;

import org.apache.camel.processor.aggregate.GroupedBodyAggregationStrategy;
import org.springframework.stereotype.Component;

import fr.aphp.referential.load.configuration.ApplicationConfiguration;

import static fr.aphp.referential.load.util.CamelUtils.TO_DB_REFERENTIAL_ROUTE_ID;
import static org.apache.camel.Exchange.FILE_NAME_ONLY;
import static org.apache.camel.Exchange.SPLIT_COMPLETE;

@Component
public class ToDbReferentialRoute extends BaseRoute {
    private final ApplicationConfiguration applicationConfiguration;

    public ToDbReferentialRoute(ApplicationConfiguration applicationConfiguration) {
        this.applicationConfiguration = applicationConfiguration;

        setInput(direct(TO_DB_REFERENTIAL_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(TO_DB_REFERENTIAL_ROUTE_ID)

                .aggregate(header(FILE_NAME_ONLY), new GroupedBodyAggregationStrategy())
                .eagerCheckCompletion()
                .completionSize(applicationConfiguration.getBatchSize())
                .completionPredicate(exchangeProperty(SPLIT_COMPLETE))

                .to(mybatisBatchInsert("upsertReferential"));
    }

}
