package fr.aphp.referential.load.route.mo.referential.f001;

import java.io.File;

import org.springframework.stereotype.Component;

import fr.aphp.referential.load.processor.mo.referential.f001.MoReferentialProcessor;
import fr.aphp.referential.load.route.BaseRoute;

import static fr.aphp.referential.load.util.CamelUtils.DISPATCH_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.MO_REFERENTIAL_F001_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.UTILS_SPLIT_COMPLETE;
import static org.apache.camel.Exchange.SPLIT_COMPLETE;

@Component(MO_REFERENTIAL_F001_ROUTE_ID)
public class MoReferentialRoute extends BaseRoute {
    public MoReferentialRoute() {
        setInput(direct(MO_REFERENTIAL_F001_ROUTE_ID));
        setOutput(direct(DISPATCH_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(MO_REFERENTIAL_F001_ROUTE_ID)

                .transform().body(File.class, MoReferentialProcessor::xlsRows)

                .split(body()).parallelProcessing()
                .setHeader(UTILS_SPLIT_COMPLETE, exchangeProperty(SPLIT_COMPLETE))

                .transform().message(MoReferentialProcessor::optionalMoReferentialMessage)

                .to(getOutput());
    }
}
