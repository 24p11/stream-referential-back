package fr.aphp.referential.load.route;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import fr.aphp.referential.load.aggregator.ListAggregator;
import fr.aphp.referential.load.configuration.ApplicationConfiguration;
import fr.aphp.referential.load.processor.ToDbConceptProcessor;

import static fr.aphp.referential.load.util.CamelUtils.SOURCE_TYPE;
import static fr.aphp.referential.load.util.CamelUtils.TO_DB_CONCEPT_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.UPDATE_CONCEPT_BEAN;
import static fr.aphp.referential.load.util.CamelUtils.UTILS_SPLIT_COMPLETE;
import static java.util.concurrent.TimeUnit.SECONDS;

@Component
@Order(1)
public class ToDbConceptRoute extends BaseRoute {
    private static final Logger LOGGER = LoggerFactory.getLogger(ToDbConceptRoute.class);
    private final ApplicationConfiguration applicationConfiguration;

    public ToDbConceptRoute(ApplicationConfiguration applicationConfiguration) {
        this.applicationConfiguration = applicationConfiguration;

        setInput(direct(TO_DB_CONCEPT_ROUTE_ID));
        setOutputs(updateConceptEndDateAfterLoad(), vocabulariesId());
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        mysqlDeadlockExceptionHandler();

        from(getInput())
                .routeId(TO_DB_CONCEPT_ROUTE_ID)

                .process(e -> e.getIn())
                .aggregate(header(SOURCE_TYPE), new ListAggregator())
                .completeAllOnStop()
                .completionSize(applicationConfiguration.getBatchSize())
                .completionTimeout(SECONDS.toMillis(5))
                .completionPredicate(header(UTILS_SPLIT_COMPLETE))

                .filter(body().isNotEqualTo(Collections.emptyList()))
                .to(mybatisBatchInsert("upsertConcept"))
                .end()

                // Not available for all stream...
                .filter(header(UTILS_SPLIT_COMPLETE))

                .process().message(ToDbConceptProcessor::setUpdateConceptBeanHeader)

                .multicast()
                .stopOnException()
                .to(getOutputs())
                .end()

                .log("End processing concept from '${header.CamelFileName}'");
    }

    private static String updateConceptEndDateAfterLoad() {
        return mybatis("updateConceptEndDateAfterLoad", "UpdateList", "inputHeader=" + UPDATE_CONCEPT_BEAN);
    }

    private static String vocabulariesId() {
        return mybatis("vocabulariesId", "Insert", "inputHeader=" + SOURCE_TYPE);
    }
}
