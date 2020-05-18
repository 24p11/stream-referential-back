package fr.aphp.referential.load.route.mo.indication.f001;

import org.apache.camel.model.dataformat.BindyType;
import org.springframework.stereotype.Component;

import fr.aphp.referential.load.message.mo.indication.f001.MoIndicationMessage;
import fr.aphp.referential.load.route.BaseRoute;

import static fr.aphp.referential.load.util.CamelUtils.DISPATCH_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.MO_INDICATION_F001_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.UTILS_SPLIT_COMPLETE;
import static org.apache.camel.Exchange.SPLIT_COMPLETE;

@Component(MO_INDICATION_F001_ROUTE_ID)
public class MoIndicationRoute extends BaseRoute {
    public MoIndicationRoute() {
        setInput(direct(MO_INDICATION_F001_ROUTE_ID));
        setOutput(direct(DISPATCH_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(MO_INDICATION_F001_ROUTE_ID)

                .split()
                .tokenize("[\\r]?\\n", true)
                .streaming()
                .parallelProcessing()
                .setHeader(UTILS_SPLIT_COMPLETE, exchangeProperty(SPLIT_COMPLETE))

                // Skip first line
                .filter(simple("${exchangeProperty.CamelSplitIndex} > 0"))

                .unmarshal().bindy(BindyType.Csv, MoIndicationMessage.class)
                .to(getOutput());
    }
}
