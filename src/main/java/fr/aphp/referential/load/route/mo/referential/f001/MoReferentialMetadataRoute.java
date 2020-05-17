package fr.aphp.referential.load.route.mo.referential.f001;

import org.springframework.stereotype.Component;

import fr.aphp.referential.load.processor.mo.referential.f001.MoReferentialMetadataProcessor;
import fr.aphp.referential.load.route.BaseRoute;

import static fr.aphp.referential.load.util.CamelUtils.MO_REFERENTIAL_F001_METADATA_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.TO_DB_METADATA_ROUTE_ID;

@Component(MO_REFERENTIAL_F001_METADATA_ROUTE_ID)
public class MoReferentialMetadataRoute extends BaseRoute {
    private final MoReferentialMetadataProcessor moReferentialMetadataProcessor;

    public MoReferentialMetadataRoute(MoReferentialMetadataProcessor moReferentialMetadataProcessor) {
        this.moReferentialMetadataProcessor = moReferentialMetadataProcessor;

        setInput(direct(MO_REFERENTIAL_F001_METADATA_ROUTE_ID));
        setOutput(direct(TO_DB_METADATA_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(MO_REFERENTIAL_F001_METADATA_ROUTE_ID)

                .transform().message(moReferentialMetadataProcessor::metadataMessageStream)

                .to(getOutput());
    }
}
