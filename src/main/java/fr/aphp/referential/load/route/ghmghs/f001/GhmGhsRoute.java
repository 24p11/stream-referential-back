package fr.aphp.referential.load.route.ghmghs.f001;

import java.nio.charset.StandardCharsets;

import org.apache.camel.model.dataformat.BindyType;
import org.springframework.stereotype.Component;

import fr.aphp.referential.load.message.ghmghs.f001.GhmGhsMessage;
import fr.aphp.referential.load.route.BaseRoute;

import static fr.aphp.referential.load.util.CamelUtils.GHMGHS_F001_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.TO_DB_DISPATCHER_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.UTILS_SPLIT_COMPLETE;
import static org.apache.camel.Exchange.SPLIT_COMPLETE;

@Component(GHMGHS_F001_ROUTE_ID)
public class GhmGhsRoute extends BaseRoute {
    public GhmGhsRoute() {
        setInput(direct(GHMGHS_F001_ROUTE_ID));
        setOutput(direct(TO_DB_DISPATCHER_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(GHMGHS_F001_ROUTE_ID)

                .convertBodyTo(String.class, StandardCharsets.ISO_8859_1.displayName())
                .split()
                .tokenize("[\\r]?\\n", true)
                .streaming()
                .parallelProcessing()
                .setHeader(UTILS_SPLIT_COMPLETE, exchangeProperty(SPLIT_COMPLETE))

                // Skip first line
                .filter(simple("${exchangeProperty.CamelSplitIndex} > 0"))

                .process(e -> e.getIn())
                .unmarshal().bindy(BindyType.Csv, GhmGhsMessage.class)
                .to(getOutput());
    }
}
