package fr.aphp.referential.load.route.mo.f001;

import java.util.Optional;

import org.springframework.stereotype.Component;

import fr.aphp.referential.load.processor.mo.f001.MoConceptProcessor;
import fr.aphp.referential.load.route.BaseRoute;

import static fr.aphp.referential.load.util.CamelUtils.MO_F001_CONCEPT_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.TO_DB_CONCEPT_ROUTE_ID;

@Component(MO_F001_CONCEPT_ROUTE_ID)
public class MoConceptRoute extends BaseRoute {
    public MoConceptRoute() {
        setInput(direct(MO_F001_CONCEPT_ROUTE_ID));
        setOutput(direct(TO_DB_CONCEPT_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(MO_F001_CONCEPT_ROUTE_ID)

                .transform().body(Optional.class, MoConceptProcessor::optionalConceptBean)
                .to(getOutput());
    }
}
