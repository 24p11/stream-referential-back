package fr.aphp.referential.load.route;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import fr.aphp.referential.load.aggregator.ListAggregator;
import fr.aphp.referential.load.configuration.ApplicationConfiguration;
import fr.aphp.referential.load.domain.type.db.MetadataQueryType;
import fr.aphp.referential.load.message.MetadataMessage;
import fr.aphp.referential.load.processor.ToDbMetadataProcessor;

import static fr.aphp.referential.load.util.CamelUtils.METADATA_QUERY_TYPE;
import static fr.aphp.referential.load.util.CamelUtils.SOURCE_TYPE;
import static fr.aphp.referential.load.util.CamelUtils.TO_DB_METADATA_ROUTE_ID;
import static java.util.concurrent.TimeUnit.SECONDS;

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

                .choice()
                .when(header(METADATA_QUERY_TYPE).isEqualTo(MetadataQueryType.UPSERT))

                .aggregate(header(SOURCE_TYPE), new ListAggregator())
                .completeAllOnStop()
                .completionSize(applicationConfiguration.getBatchSize())
                .completionTimeout(SECONDS.toMillis(5))

                .process(e -> System.err.println(e.getIn().getBody()))
                .to(mybatisBatchInsert("upsertMetadata"))

                .endChoice()

                .when(header(METADATA_QUERY_TYPE).isEqualTo(MetadataQueryType.UPDATE)).to(mybatis("updateMetadata", "Update"));
    }
}
