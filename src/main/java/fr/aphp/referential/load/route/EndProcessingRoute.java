package fr.aphp.referential.load.route;

import org.springframework.stereotype.Component;

import static fr.aphp.referential.load.util.CamelUtils.END_PROCESSING_ROUTE_ID;

@Component
public class EndProcessingRoute extends BaseRoute {
    public EndProcessingRoute() {
        setInput(direct(END_PROCESSING_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(END_PROCESSING_ROUTE_ID)

                .log("End processing '${header.CamelFileName}'");
    }
}
