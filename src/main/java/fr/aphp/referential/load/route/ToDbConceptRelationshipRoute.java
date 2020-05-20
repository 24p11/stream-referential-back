package fr.aphp.referential.load.route;

import org.springframework.stereotype.Component;

import fr.aphp.referential.load.aggregator.ListAggregator;
import fr.aphp.referential.load.configuration.ApplicationConfiguration;

import static fr.aphp.referential.load.util.CamelUtils.SOURCE_TYPE;
import static fr.aphp.referential.load.util.CamelUtils.TO_DB_CONCEPT_RELATIONSHIP_ROUTE_ID;
import static java.util.concurrent.TimeUnit.SECONDS;

@Component
public class ToDbConceptRelationshipRoute extends BaseRoute {
    private final ApplicationConfiguration applicationConfiguration;

    public ToDbConceptRelationshipRoute(ApplicationConfiguration applicationConfiguration) {
        this.applicationConfiguration = applicationConfiguration;

        setInput(direct(TO_DB_CONCEPT_RELATIONSHIP_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        mysqlDeadlockExceptionHandler();

        super.configure();

        from(getInput())
                .routeId(TO_DB_CONCEPT_RELATIONSHIP_ROUTE_ID)

                .aggregate(header(SOURCE_TYPE), new ListAggregator())
                .completeAllOnStop()
                .completionSize(applicationConfiguration.getBatchSize())
                .completionTimeout(SECONDS.toMillis(5))

                .to(mybatisBatchInsert("insertIgnoreConceptRelationship"))

                .log("End processing conceptRelationship from '${header.CamelFileName}'");
    }
}
