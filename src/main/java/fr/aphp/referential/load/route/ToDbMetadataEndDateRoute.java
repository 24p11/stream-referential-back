package fr.aphp.referential.load.route;

import org.springframework.stereotype.Component;

import fr.aphp.referential.load.domain.type.SourceType;

import static fr.aphp.referential.load.util.CamelUtils.TO_DB_METADATA_END_DATE_ROUTE_ID;
import static java.util.concurrent.TimeUnit.MINUTES;

@Component
public class ToDbMetadataEndDateRoute extends BaseRoute {
    public ToDbMetadataEndDateRoute() {
        setInput(timerEndpoint());
    }

    @Override
    public void configure() throws Exception {
        mysqlDeadlockExceptionHandler();

        super.configure();

        from(getInput())
                .routeId(TO_DB_METADATA_END_DATE_ROUTE_ID)

                .setBody(constant(SourceType.values()))
                .split(body())

                .log("Updating ${body} metadata 'end_date'")
                .to(updateMetadataEndDate());
    }

    private StringBuilder timerEndpoint() {
        return new StringBuilder("timer:")
                .append(TO_DB_METADATA_END_DATE_ROUTE_ID)
                .append("?period=")
                .append(MINUTES.toMillis(10));
    }

    private static String updateMetadataEndDate() {
        return mybatis("updateMetadataEndDate", "UpdateList");
    }
}
