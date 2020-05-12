package fr.aphp.referential.load.route.ghmghs.f001;

import org.springframework.stereotype.Component;

import fr.aphp.referential.load.processor.ghgghs.f001.GhmGhsProcessor;
import fr.aphp.referential.load.route.BaseRoute;

import static fr.aphp.referential.load.util.CamelUtils.GHMGHS_F001_CONCEPT_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.TO_DB_CONCEPT_ROUTE_ID;

@Component(GHMGHS_F001_CONCEPT_ROUTE_ID)
public class GhmGhsConceptRoute extends BaseRoute {
    public GhmGhsConceptRoute() {
        setInput(direct(GHMGHS_F001_CONCEPT_ROUTE_ID));
        setOutput(direct(TO_DB_CONCEPT_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(GHMGHS_F001_CONCEPT_ROUTE_ID)

                .transform().message(GhmGhsProcessor::streamConceptBean)

                .split(body())
                .parallelProcessing()

                .process().message(GhmGhsProcessor::setSourceTypeHeader)
                .to(getOutput());
    }
}
