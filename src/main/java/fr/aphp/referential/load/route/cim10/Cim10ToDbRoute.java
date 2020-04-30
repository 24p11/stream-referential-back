package fr.aphp.referential.load.route.cim10;

import org.apache.camel.processor.aggregate.GroupedBodyAggregationStrategy;
import org.springframework.stereotype.Component;

import fr.aphp.referential.load.configuration.ApplicationConfiguration;
import fr.aphp.referential.load.route.BaseRoute;

import static fr.aphp.referential.load.util.CamelUtils.CIM10_TO_DB_ROUTE_ID;
import static org.apache.camel.Exchange.FILE_NAME_ONLY;
import static org.apache.camel.Exchange.SPLIT_COMPLETE;

@Component
public class Cim10ToDbRoute extends BaseRoute {
    private final ApplicationConfiguration applicationConfiguration;

    public Cim10ToDbRoute(ApplicationConfiguration applicationConfiguration) {
        this.applicationConfiguration = applicationConfiguration;

        setInput(direct(CIM10_TO_DB_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(CIM10_TO_DB_ROUTE_ID)

                .aggregate(header(FILE_NAME_ONLY), new GroupedBodyAggregationStrategy())
                .eagerCheckCompletion()
                .completionSize(applicationConfiguration.getBatchSize())
                .completionPredicate(exchangeProperty(SPLIT_COMPLETE))

                .to(mybatisBatchInsert("upsertReferential"));
    }
}
