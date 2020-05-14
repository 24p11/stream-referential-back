package fr.aphp.referential.load.route;

import org.springframework.stereotype.Component;

import static fr.aphp.referential.load.util.CamelUtils.TO_DB_METADATA_DICTIONARY_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.TO_DB_METADATA_END_DATE_ROUTE_ID;
import static java.util.concurrent.TimeUnit.MINUTES;

@Component
public class ToDbMetadataDictionaryRoute extends BaseRoute {
    public ToDbMetadataDictionaryRoute() {
        setInput(timerEndpoint());
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        mysqlDeadlockExceptionHandler();

        from(getInput())
                .routeId(TO_DB_METADATA_DICTIONARY_ROUTE_ID)

                .log("Updating metadata dictionary list")
                .to(mybatisBatchInsert("insertIgnoreMetadataDictionary"));
    }

    private StringBuilder timerEndpoint() {
        return new StringBuilder("timer:")
                .append(TO_DB_METADATA_END_DATE_ROUTE_ID)
                .append("?period=")
                .append(MINUTES.toMillis(15));
    }
}
