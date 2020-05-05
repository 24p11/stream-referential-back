package fr.aphp.referential.load.route.ccam.f002;

import org.springframework.stereotype.Component;

import fr.aphp.referential.load.processor.ccam.f002.CcamConceptProcessor;
import fr.aphp.referential.load.route.BaseRoute;

import static fr.aphp.referential.load.util.CamelUtils.CCAM_F002_CONCEPT_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.TO_DB_CONCEPT_ROUTE_ID;

@Component(CCAM_F002_CONCEPT_ROUTE_ID)
public class CcamConceptRoute extends BaseRoute {
    public CcamConceptRoute() {
        setInput(direct(CCAM_F002_CONCEPT_ROUTE_ID));
        setOutput(direct(TO_DB_CONCEPT_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(CCAM_F002_CONCEPT_ROUTE_ID)

                .transform().message(CcamConceptProcessor::optionalConceptBean)
                .to(getOutput());
    }
}
