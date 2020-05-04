package fr.aphp.referential.load.route.ccam.f002;

import org.springframework.stereotype.Component;

import fr.aphp.referential.load.route.BaseRoute;

import static fr.aphp.referential.load.util.CamelUtils.CCAM_F002_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.CCAM_OUTPUT_ROUTE_ID;

@Component
public class CcamRoute extends BaseRoute {
    public CcamRoute() {
        setInput(direct(CCAM_F002_ROUTE_ID));
        setOutput(direct(CCAM_OUTPUT_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(CCAM_F002_ROUTE_ID)

                .to(getOutput());
    }
}
