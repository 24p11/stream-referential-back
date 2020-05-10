package fr.aphp.referential.load.route.ccam.f003;

import java.nio.charset.StandardCharsets;

import org.apache.camel.model.dataformat.BindyType;
import org.springframework.stereotype.Component;

import fr.aphp.referential.load.message.ccam.f003.CcamMessage;
import fr.aphp.referential.load.route.BaseRoute;

import static fr.aphp.referential.load.util.CamelUtils.CCAM_F003_METADATA_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.CCAM_F003_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.UTILS_SPLIT_COMPLETE;
import static org.apache.camel.Exchange.SPLIT_COMPLETE;

@Component(CCAM_F003_ROUTE_ID)
public class CcamRoute extends BaseRoute {
    public CcamRoute() {
        setInput(direct(CCAM_F003_ROUTE_ID));
        setOutput(direct(CCAM_F003_METADATA_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(CCAM_F003_ROUTE_ID)

                .convertBodyTo(String.class, StandardCharsets.ISO_8859_1.displayName())

                .split()
                .tokenize("[\\r]?\\n", true)
                .streaming()
                .parallelProcessing()
                .setHeader(UTILS_SPLIT_COMPLETE, exchangeProperty(SPLIT_COMPLETE))

                .unmarshal().bindy(BindyType.Csv, CcamMessage.class)

                .to(getOutput());
    }
}
