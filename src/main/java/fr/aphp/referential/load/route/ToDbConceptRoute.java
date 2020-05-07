package fr.aphp.referential.load.route;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import fr.aphp.referential.load.aggregator.ListAggregator;
import fr.aphp.referential.load.configuration.ApplicationConfiguration;
import fr.aphp.referential.load.processor.ToDbConceptProcessor;

import static fr.aphp.referential.load.util.CamelUtils.FILE_SPLIT_COMPLETE;
import static fr.aphp.referential.load.util.CamelUtils.TO_DB_CONCEPT_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.UPDATE_CONCEPT_BEAN;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.apache.camel.Exchange.FILE_NAME_ONLY;
import static org.apache.camel.Exchange.SPLIT_COMPLETE;

@Component
public class ToDbConceptRoute extends BaseRoute {
    private static final Logger LOGGER = LoggerFactory.getLogger(ToDbConceptRoute.class);
    private final ApplicationConfiguration applicationConfiguration;

    public ToDbConceptRoute(ApplicationConfiguration applicationConfiguration) {
        this.applicationConfiguration = applicationConfiguration;

        setInput(direct(TO_DB_CONCEPT_ROUTE_ID));
        setOutputs(updateConceptEndDateAfterLoad(), updateMetadataEndDate());
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        mysqlDeadlockExceptionHandler();

        from(getInput())
                .routeId(TO_DB_CONCEPT_ROUTE_ID)

                .aggregate(header(FILE_NAME_ONLY), new ListAggregator())
                .completeAllOnStop()
                .eagerCheckCompletion()
                .completionSize(applicationConfiguration.getBatchSize())
                .completionTimeout(SECONDS.toMillis(5))
                .completionPredicate(exchangeProperty(SPLIT_COMPLETE))

                .filter(body().isNotEqualTo(Collections.emptyList()))
                .to(mybatisBatchInsert("upsertConcept"))
                .end()

                .filter(header(FILE_SPLIT_COMPLETE))

                .process().message(ToDbConceptProcessor::setUpdateConceptBeanHeader)

                .multicast()
                .stopOnException()
                .to(getOutputs());
    }

    private static String updateConceptEndDateAfterLoad() {
        return mybatis("updateConceptEndDateAfterLoad", "UpdateList", "inputHeader=" + UPDATE_CONCEPT_BEAN);
    }

    private static String updateMetadataEndDate() {
        return mybatis("updateMetadataEndDate", "UpdateList", "inputHeader=" + UPDATE_CONCEPT_BEAN);
    }
}
