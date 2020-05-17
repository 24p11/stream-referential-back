package fr.aphp.referential.load.route.mo.referential.f001;

import java.util.Optional;

import org.springframework.stereotype.Component;

import fr.aphp.referential.load.processor.mo.referential.f001.MoReferentialConceptProcessor;
import fr.aphp.referential.load.route.BaseRoute;

import static fr.aphp.referential.load.util.CamelUtils.MO_REFERENTIAL_F001_CONCEPT_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.TO_DB_CONCEPT_ROUTE_ID;

@Component(MO_REFERENTIAL_F001_CONCEPT_ROUTE_ID)
public class MoReferentialConceptRoute extends BaseRoute {
    public MoReferentialConceptRoute() {
        setInput(direct(MO_REFERENTIAL_F001_CONCEPT_ROUTE_ID));
        setOutput(direct(TO_DB_CONCEPT_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(MO_REFERENTIAL_F001_CONCEPT_ROUTE_ID)

                .transform().body(Optional.class, MoReferentialConceptProcessor::optionalConceptBean)
                .to(getOutput());
    }
}
