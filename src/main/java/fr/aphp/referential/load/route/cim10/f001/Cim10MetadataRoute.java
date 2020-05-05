package fr.aphp.referential.load.route.cim10.f001;

import org.springframework.stereotype.Component;

import fr.aphp.referential.load.processor.cim10.f001.Cim10MetadataProcessor;
import fr.aphp.referential.load.route.BaseRoute;

import static fr.aphp.referential.load.util.CamelUtils.CIM10_METADATA_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.TO_DB_METADATA_ROUTE_ID;

@Component
public class Cim10MetadataRoute extends BaseRoute {
    public Cim10MetadataRoute() {
        setInput(direct(CIM10_METADATA_ROUTE_ID));
        setOutput(direct(TO_DB_METADATA_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(CIM10_METADATA_ROUTE_ID)

                .transform().message(Cim10MetadataProcessor::metadataBeanStream)

                .split(body()).parallelProcessing()
                .to(getOutput());
    }
}