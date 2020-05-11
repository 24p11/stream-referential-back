package fr.aphp.referential.load.route;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import fr.aphp.referential.load.aggregator.ListAggregator;
import fr.aphp.referential.load.configuration.ApplicationConfiguration;
import fr.aphp.referential.load.message.MetadataMessage;
import fr.aphp.referential.load.processor.ToDbMetadataProcessor;

import static fr.aphp.referential.load.util.CamelUtils.TO_DB_METADATA_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.UPDATE_CONCEPT_BEAN;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.apache.camel.Exchange.FILE_NAME_ONLY;

@Component
public class ToDbMetadataRoute extends BaseRoute {
    private static final Logger LOGGER = LoggerFactory.getLogger(ToDbMetadataRoute.class);
    private final ApplicationConfiguration applicationConfiguration;
    private final ToDbMetadataProcessor toDbMetadataProcessor;

    public ToDbMetadataRoute(ApplicationConfiguration applicationConfiguration, ToDbMetadataProcessor toDbMetadataProcessor) {
        this.applicationConfiguration = applicationConfiguration;
        this.toDbMetadataProcessor = toDbMetadataProcessor;

        setInput(direct(TO_DB_METADATA_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        mysqlDeadlockExceptionHandler();

        from(getInput())
                .routeId(TO_DB_METADATA_ROUTE_ID)

                // Stream<MetadataMessage>
                .split(body()).parallelProcessing()

                .transform().body(MetadataMessage.class, toDbMetadataProcessor::metadataBean)

                .aggregate(header(FILE_NAME_ONLY), new ListAggregator())
                .completeAllOnStop()
                .completionSize(applicationConfiguration.getBatchSize())
                .completionTimeout(SECONDS.toMillis(5))

                .to(mybatisBatchInsert("upsertMetadata"))

        // TODO
                /*.filter(header(UTILS_SPLIT_COMPLETE))

                .to(updateMetadataEndDate())

                .log("End processing metadata from '${header.CamelFileName}'")*/;
    }

    private static String updateMetadataEndDate() {
        return mybatis("updateMetadataEndDate", "UpdateList", "inputHeader=" + UPDATE_CONCEPT_BEAN);
    }
}
