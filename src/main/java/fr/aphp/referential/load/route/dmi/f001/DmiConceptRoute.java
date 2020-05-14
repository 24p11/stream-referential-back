package fr.aphp.referential.load.route.dmi.f001;

import java.util.Optional;

import org.springframework.stereotype.Component;

import fr.aphp.referential.load.processor.dmi.f001.DmiConceptProcessor;
import fr.aphp.referential.load.route.BaseRoute;

import static fr.aphp.referential.load.util.CamelUtils.DMI_F001_CONCEPT_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.TO_DB_CONCEPT_ROUTE_ID;

@Component(DMI_F001_CONCEPT_ROUTE_ID)
public class DmiConceptRoute extends BaseRoute {
    public DmiConceptRoute() {
        setInput(direct(DMI_F001_CONCEPT_ROUTE_ID));
        setOutput(direct(TO_DB_CONCEPT_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(DMI_F001_CONCEPT_ROUTE_ID)

                .transform().body(Optional.class, DmiConceptProcessor::optionalConceptBean)
                .to(getOutput());
    }
}
