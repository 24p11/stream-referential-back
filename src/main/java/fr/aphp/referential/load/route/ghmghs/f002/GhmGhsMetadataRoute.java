package fr.aphp.referential.load.route.ghmghs.f002;

import org.springframework.stereotype.Component;

import fr.aphp.referential.load.processor.ghmghs.f002.GhmGhsMetadataProcessor;
import fr.aphp.referential.load.route.BaseRoute;

import static fr.aphp.referential.load.util.CamelUtils.GHMGHS_F002_METADATA_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.TO_DB_METADATA_ROUTE_ID;

@Component(GHMGHS_F002_METADATA_ROUTE_ID)
public class GhmGhsMetadataRoute extends BaseRoute {
    private final GhmGhsMetadataProcessor ghmGhsMetadataProcessor;

    public GhmGhsMetadataRoute(GhmGhsMetadataProcessor ghmGhsMetadataProcessor) {
        this.ghmGhsMetadataProcessor = ghmGhsMetadataProcessor;

        setInput(direct(GHMGHS_F002_METADATA_ROUTE_ID));
        setOutput(direct(TO_DB_METADATA_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(GHMGHS_F002_METADATA_ROUTE_ID)

                .transform().message(ghmGhsMetadataProcessor::metadataMessageStream)

                .to(getOutput());
    }
}
