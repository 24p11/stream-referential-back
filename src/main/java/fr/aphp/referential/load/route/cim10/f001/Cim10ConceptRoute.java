package fr.aphp.referential.load.route.cim10.f001;

import org.springframework.stereotype.Component;

import fr.aphp.referential.load.processor.cim10.f001.Cim10ConceptProcessor;
import fr.aphp.referential.load.route.BaseRoute;

import static fr.aphp.referential.load.util.CamelUtils.CIM10_F001_CONCEPT_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.TO_DB_CONCEPT_ROUTE_ID;

@Component(CIM10_F001_CONCEPT_ROUTE_ID)
public class Cim10ConceptRoute extends BaseRoute {
    public Cim10ConceptRoute() {
        setInput(direct(CIM10_F001_CONCEPT_ROUTE_ID));
        setOutput(direct(TO_DB_CONCEPT_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(CIM10_F001_CONCEPT_ROUTE_ID)

                .transform().message(Cim10ConceptProcessor::optionalConceptBean)
                .to(getOutput());
    }
}
