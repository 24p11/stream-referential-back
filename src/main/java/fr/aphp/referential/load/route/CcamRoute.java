package fr.aphp.referential.load.route;

import org.springframework.stereotype.Component;

import static fr.aphp.referential.load.domain.type.CcamFormatType.F001;
import static fr.aphp.referential.load.domain.type.CcamFormatType.F002;
import static fr.aphp.referential.load.domain.type.SourceType.CCAM;
import static fr.aphp.referential.load.util.CamelUtils.CCAM_F001_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.CCAM_F002_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.CCAM_METADATA_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.CCAM_OUTPUT_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.CCAM_REFERENTIAL_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.CCAM_ROUTE_ID;

@Component
public class CcamRoute extends BaseRoute {

    public CcamRoute() {
        setInput(direct(CCAM));
        setOutputs(direct(CCAM_REFERENTIAL_ROUTE_ID), direct(CCAM_METADATA_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        // Input
        from(getInput())
                .routeId(CCAM_ROUTE_ID)

                .choice()
                .when(isFormat(F001)).to(direct(CCAM_F001_ROUTE_ID))
                .when(isFormat(F002)).to(direct(CCAM_F002_ROUTE_ID));

        // Output concept & metadata
        from(direct(CCAM_OUTPUT_ROUTE_ID))
                .routeId(CCAM_OUTPUT_ROUTE_ID)

                .multicast()
                .stopOnException()
                .to(getOutputs())
                .end();
    }
}
