package fr.aphp.referential.load.route.ccam.f001;

import org.springframework.stereotype.Component;

import fr.aphp.referential.load.processor.ccam.f001.CcamMetadataProcessor;
import fr.aphp.referential.load.route.BaseRoute;

import static fr.aphp.referential.load.util.CamelUtils.CCAM_METADATA_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.TO_DB_METADATA_ROUTE_ID;

@Component
public class CcamMetadataRoute extends BaseRoute {
    public CcamMetadataRoute() {
        setInput(direct(CCAM_METADATA_ROUTE_ID));
        setOutput(direct(TO_DB_METADATA_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(CCAM_METADATA_ROUTE_ID)

                .transform().message(CcamMetadataProcessor::metadataBeanStream)

                .split(body()).parallelProcessing()
                .to(getOutput());
    }
}
