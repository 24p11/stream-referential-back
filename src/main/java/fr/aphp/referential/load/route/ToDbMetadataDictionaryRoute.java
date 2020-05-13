package fr.aphp.referential.load.route;

import org.springframework.stereotype.Component;

import fr.aphp.referential.load.aggregator.ListAggregator;
import fr.aphp.referential.load.configuration.ApplicationConfiguration;
import fr.aphp.referential.load.message.MetadataMessage;
import fr.aphp.referential.load.processor.ToDbMetadataDictionaryProcessor;

import static fr.aphp.referential.load.util.CamelUtils.SOURCE_TYPE;
import static fr.aphp.referential.load.util.CamelUtils.TO_DB_METADATA_DICTIONARY_ROUTE_ID;
import static java.util.concurrent.TimeUnit.SECONDS;

@Component
public class ToDbMetadataDictionaryRoute extends BaseRoute {
    private final ApplicationConfiguration applicationConfiguration;

    public ToDbMetadataDictionaryRoute(ApplicationConfiguration applicationConfiguration) {
        this.applicationConfiguration = applicationConfiguration;

        setInput(direct(TO_DB_METADATA_DICTIONARY_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        mysqlDeadlockExceptionHandler();

        from(getInput())
                .routeId(TO_DB_METADATA_DICTIONARY_ROUTE_ID)

                .transform().body(MetadataMessage.class, ToDbMetadataDictionaryProcessor::metadataDictionaryBean)

                .aggregate(header(SOURCE_TYPE), new ListAggregator())
                .completeAllOnStop()
                .completionSize(applicationConfiguration.getBatchSize())
                .completionTimeout(SECONDS.toMillis(5))

                .to(mybatisBatchInsert("insertIgnoreMetadataDictionary"));
    }
}
