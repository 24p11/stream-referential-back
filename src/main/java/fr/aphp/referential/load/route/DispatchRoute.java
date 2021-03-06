package fr.aphp.referential.load.route;

import org.apache.camel.Message;
import org.springframework.stereotype.Component;

import static fr.aphp.referential.load.util.CamelUtils.CONCEPT_TABLE;
import static fr.aphp.referential.load.util.CamelUtils.DISPATCH_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.FORMAT;
import static fr.aphp.referential.load.util.CamelUtils.METADATA_TABLE;
import static fr.aphp.referential.load.util.CamelUtils.SOURCE_TYPE;

@Component
public class DispatchRoute extends BaseRoute {
    public DispatchRoute() {
        setInput(direct(DISPATCH_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(DISPATCH_ROUTE_ID)

                .recipientList()
                .message(this::endpointProducerBuilders)
                .ignoreInvalidEndpoints()
                .stopOnException()
                .end();
    }

    private String[] endpointProducerBuilders(Message message) {
        String sourceType = message.getHeader(SOURCE_TYPE, String.class);
        String format = message.getHeader(FORMAT, String.class);
        return new String[]{
                directUnchecked(dynamicRouteId(CONCEPT_TABLE, sourceType, format)),
                directUnchecked(dynamicRouteId(METADATA_TABLE, sourceType, format))
        };
    }
}
