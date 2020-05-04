package fr.aphp.referential.load.route;

import java.nio.charset.StandardCharsets;

import org.apache.camel.model.dataformat.BindyType;
import org.springframework.stereotype.Component;

import fr.aphp.referential.load.message.cim10.f001.Cim10Message;

import static fr.aphp.referential.load.domain.type.Cim10FormatType.F001;
import static fr.aphp.referential.load.domain.type.SourceType.CIM10;
import static fr.aphp.referential.load.util.CamelUtils.CIM10_METADATA_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.CIM10_REFERENTIAL_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.CIM10_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.FILE_SPLIT_COMPLETE;
import static org.apache.camel.Exchange.SPLIT_COMPLETE;

@Component
public class Cim10Route extends BaseRoute {
    public Cim10Route() {
        setInput(direct(CIM10));
        setOutputs(direct(CIM10_REFERENTIAL_ROUTE_ID), direct(CIM10_METADATA_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(CIM10_ROUTE_ID)

                .convertBodyTo(String.class, StandardCharsets.ISO_8859_1.displayName())
                .split().tokenize("[\\r]?\\n", true).streaming()
                .setHeader(FILE_SPLIT_COMPLETE, exchangeProperty(SPLIT_COMPLETE))

                .choice()
                // F001
                .when(isFormat(F001))

                .unmarshal().bindy(BindyType.Csv, Cim10Message.class)

                .multicast()
                .stopOnException()
                .to(getOutputs())
                .end();

    }
}
