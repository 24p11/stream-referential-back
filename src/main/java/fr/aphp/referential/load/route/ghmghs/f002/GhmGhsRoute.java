package fr.aphp.referential.load.route.ghmghs.f002;

import org.apache.camel.model.dataformat.BindyType;
import org.springframework.stereotype.Component;

import fr.aphp.referential.load.message.ghmghs.f002.GhmGhsMessage;
import fr.aphp.referential.load.route.BaseRoute;

import static fr.aphp.referential.load.util.CamelUtils.DISPATCH_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.GHMGHS_F002_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.UTILS_SPLIT_COMPLETE;
import static org.apache.camel.Exchange.SPLIT_COMPLETE;

@Component(GHMGHS_F002_ROUTE_ID)
public class GhmGhsRoute extends BaseRoute {
    public GhmGhsRoute() {
        setInput(direct(GHMGHS_F002_ROUTE_ID));
        setOutput(direct(DISPATCH_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(GHMGHS_F002_ROUTE_ID)

                .unmarshal().bindy(BindyType.Csv, GhmGhsMessage.class)

                .split(body())
                .parallelProcessing()
                .setHeader(UTILS_SPLIT_COMPLETE, exchangeProperty(SPLIT_COMPLETE))

                .to(getOutput());
    }
}