package fr.aphp.referential.load.route.ccam.f001;

import org.springframework.stereotype.Component;

import fr.aphp.referential.load.processor.ccam.f001.CcamReferentialProcessor;
import fr.aphp.referential.load.route.BaseRoute;

import static fr.aphp.referential.load.util.CamelUtils.CCAM_REFERENTIAL_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.TO_DB_REFERENTIAL_ROUTE_ID;

@Component
public class CcamReferentialRoute extends BaseRoute {
    public CcamReferentialRoute() {
        setInput(direct(CCAM_REFERENTIAL_ROUTE_ID));
        setOutput(direct(TO_DB_REFERENTIAL_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(CCAM_REFERENTIAL_ROUTE_ID)

                .transform().message(CcamReferentialProcessor::optionalReferentialBean)
                .to(getOutput());
    }
}
