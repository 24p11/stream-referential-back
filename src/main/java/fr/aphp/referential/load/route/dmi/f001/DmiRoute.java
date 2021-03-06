package fr.aphp.referential.load.route.dmi.f001;

import java.io.File;

import org.springframework.stereotype.Component;

import fr.aphp.referential.load.processor.dmi.f001.DmiProcessor;
import fr.aphp.referential.load.route.BaseRoute;

import static fr.aphp.referential.load.util.CamelUtils.DISPATCH_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.DMI_F001_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.UTILS_SPLIT_COMPLETE;
import static org.apache.camel.Exchange.SPLIT_COMPLETE;

@Component(DMI_F001_ROUTE_ID)
public class DmiRoute extends BaseRoute {
    public DmiRoute() {
        setInput(direct(DMI_F001_ROUTE_ID));
        setOutput(direct(DISPATCH_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(DMI_F001_ROUTE_ID)

                .transform().body(File.class, DmiProcessor::xlsRows)

                .split(body()).parallelProcessing()
                .setHeader(UTILS_SPLIT_COMPLETE, exchangeProperty(SPLIT_COMPLETE))

                .transform().message(DmiProcessor::optionalDmiMessage)

                .to(getOutput());
    }
}
