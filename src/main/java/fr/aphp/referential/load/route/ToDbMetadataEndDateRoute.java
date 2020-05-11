package fr.aphp.referential.load.route;

import org.apache.camel.builder.EndpointConsumerBuilder;
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
        super.configure();

        from(getInput())
                .routeId(TO_DB_METADATA_END_DATE_ROUTE_ID)

                .setBody(constant(SourceType.values()))
                .split(body())

                .log("Updating ${body} metadata 'end_date'")
                .to(updateMetadataEndDate());
    }

    private EndpointConsumerBuilder timerEndpoint() {
        return timer(TO_DB_METADATA_END_DATE_ROUTE_ID)
                .period(MINUTES.toMillis(10));
    }

    private static String updateMetadataEndDate() {
        return mybatis("updateMetadataEndDate", "UpdateList");
    }
}
