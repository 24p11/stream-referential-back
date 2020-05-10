package fr.aphp.referential.load.route.cim10.f001;

import org.springframework.stereotype.Component;

import fr.aphp.referential.load.processor.cim10.f001.Cim10MetadataProcessor;
import fr.aphp.referential.load.route.BaseRoute;

import static fr.aphp.referential.load.util.CamelUtils.CIM10_F001_METADATA_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.TO_DB_METADATA_ROUTE_ID;

@Component(CIM10_F001_METADATA_ROUTE_ID)
public class Cim10MetadataRoute extends BaseRoute {
    private final Cim10MetadataProcessor cim10MetadataProcessor;

    public Cim10MetadataRoute(Cim10MetadataProcessor cim10MetadataProcessor) {
        this.cim10MetadataProcessor = cim10MetadataProcessor;

        setInput(direct(CIM10_F001_METADATA_ROUTE_ID));
        setOutput(direct(TO_DB_METADATA_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(CIM10_F001_METADATA_ROUTE_ID)

                .transform().message(cim10MetadataProcessor::metadataMessageStream)

                .to(getOutput());
    }
}
