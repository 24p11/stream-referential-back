package fr.aphp.referential.load.route;

import java.io.File;

import org.springframework.stereotype.Component;

import fr.aphp.referential.load.processor.ccam.f001.CcamProcessor;

import static fr.aphp.referential.load.domain.type.CcamFormatType.F001;
import static fr.aphp.referential.load.domain.type.SourceType.CCAM;
import static fr.aphp.referential.load.util.CamelUtils.CCAM_METADATA_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.CCAM_REFERENTIAL_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.CCAM_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.FILE_SPLIT_COMPLETE;
import static org.apache.camel.Exchange.SPLIT_COMPLETE;

@Component
public class CcamRoute extends BaseRoute {

    public CcamRoute() {
        setInput(direct(CCAM));
        setOutputs(direct(CCAM_REFERENTIAL_ROUTE_ID), direct(CCAM_METADATA_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(CCAM_ROUTE_ID)

                .choice()
                // F001
                .when(isFormat(F001))

                .transform().body(File.class, CcamProcessor::xlsRows)

                .split(body())
                .setHeader(FILE_SPLIT_COMPLETE, exchangeProperty(SPLIT_COMPLETE))

                .transform().message(CcamProcessor::optionalCcamMessage)

                .multicast()
                .stopOnException()
                .to(getOutputs())
                .end();
    }
}
