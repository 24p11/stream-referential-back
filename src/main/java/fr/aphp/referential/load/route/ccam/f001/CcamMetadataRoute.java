package fr.aphp.referential.load.route.ccam.f001;

import org.springframework.stereotype.Component;

import fr.aphp.referential.load.processor.ccam.f001.CcamMetadataProcessor;
import fr.aphp.referential.load.route.BaseRoute;

import static fr.aphp.referential.load.util.CamelUtils.CCAM_F001_METADATA_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.TO_DB_METADATA_ROUTE_ID;

@Component
public class CcamMetadataRoute extends BaseRoute {
    private final CcamMetadataProcessor ccamMetadataProcessor;

    public CcamMetadataRoute(CcamMetadataProcessor ccamMetadataProcessor) {
        this.ccamMetadataProcessor = ccamMetadataProcessor;

        setInput(direct(CCAM_F001_METADATA_ROUTE_ID));
        setOutput(direct(TO_DB_METADATA_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(CCAM_F001_METADATA_ROUTE_ID)

                .transform().message(ccamMetadataProcessor::metadataMessageStream)

                .to(getOutput());
    }
}
