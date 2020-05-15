package fr.aphp.referential.load.route.cim10.f001;

import java.nio.charset.StandardCharsets;

import org.apache.camel.model.dataformat.BindyType;
import org.springframework.stereotype.Component;

import fr.aphp.referential.load.message.cim10.f001.Cim10Message;
import fr.aphp.referential.load.route.BaseRoute;

import static fr.aphp.referential.load.util.CamelUtils.CIM10_F001_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.DISPATCH_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.UTILS_SPLIT_COMPLETE;
import static org.apache.camel.Exchange.SPLIT_COMPLETE;

@Component(CIM10_F001_ROUTE_ID)
public class Cim10Route extends BaseRoute {
    public Cim10Route() {
        setInput(direct(CIM10_F001_ROUTE_ID));
        setOutput(direct(DISPATCH_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(CIM10_F001_ROUTE_ID)

                .convertBodyTo(String.class, StandardCharsets.ISO_8859_1.displayName())
                .split()
                .tokenize("[\\r]?\\n", true)
                .streaming()
                .parallelProcessing()
                .setHeader(UTILS_SPLIT_COMPLETE, exchangeProperty(SPLIT_COMPLETE))

                .unmarshal().bindy(BindyType.Csv, Cim10Message.class)

                .to(getOutput());
    }
}
