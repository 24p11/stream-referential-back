package fr.aphp.referential.load.route.ccam.f001;

import java.io.File;

import org.springframework.stereotype.Component;

import fr.aphp.referential.load.processor.ccam.f001.CcamProcessor;
import fr.aphp.referential.load.route.BaseRoute;

import static fr.aphp.referential.load.util.CamelUtils.CCAM_F001_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.DISPATCH_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.UTILS_SPLIT_COMPLETE;
import static org.apache.camel.Exchange.SPLIT_COMPLETE;

@Component(CCAM_F001_ROUTE_ID)
public class CcamRoute extends BaseRoute {
    public CcamRoute() {
        setInput(direct(CCAM_F001_ROUTE_ID));
        setOutput(direct(DISPATCH_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(CCAM_F001_ROUTE_ID)

                .transform().body(File.class, CcamProcessor::xlsRows)

                .split(body()).parallelProcessing()
                .setHeader(UTILS_SPLIT_COMPLETE, exchangeProperty(SPLIT_COMPLETE))

                .transform().message(CcamProcessor::optionalCcamMessage)

                .to(getOutput());
    }
}
