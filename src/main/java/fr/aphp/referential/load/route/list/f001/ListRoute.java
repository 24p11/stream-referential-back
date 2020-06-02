package fr.aphp.referential.load.route.list.f001;

import org.apache.camel.Exchange;
import org.apache.camel.model.dataformat.BindyType;
import org.springframework.stereotype.Component;

import fr.aphp.referential.load.message.list.f001.ListMessage;
import fr.aphp.referential.load.processor.list.f001.ListProcessor;
import fr.aphp.referential.load.route.BaseRoute;

import static fr.aphp.referential.load.util.CamelUtils.DISPATCH_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.LIST_F001_ROUTE_ID;

@Component(LIST_F001_ROUTE_ID)
public class ListRoute extends BaseRoute {
    public ListRoute() {
        setInput(direct(LIST_F001_ROUTE_ID));
        setOutput(direct(DISPATCH_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(LIST_F001_ROUTE_ID)

                .process().exchange(ListProcessor::setHeaders)

                .split()
                .tokenize("\n")

                .filter(exchangeProperty(Exchange.SPLIT_INDEX).isGreaterThan(5))

                .unmarshal().bindy(BindyType.Csv, ListMessage.class)

                .to(getOutput());
    }
}
