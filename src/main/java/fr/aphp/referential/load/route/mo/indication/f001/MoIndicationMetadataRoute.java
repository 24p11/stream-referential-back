package fr.aphp.referential.load.route.mo.indication.f001;

import org.springframework.stereotype.Component;

import fr.aphp.referential.load.processor.mo.indication.f001.MoIndicationMetadataProcessor;
import fr.aphp.referential.load.route.BaseRoute;

import static fr.aphp.referential.load.util.CamelUtils.MO_INDICATION_F001_METADATA_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.TO_DB_METADATA_ROUTE_ID;

@Component(MO_INDICATION_F001_METADATA_ROUTE_ID)
public class MoIndicationMetadataRoute extends BaseRoute {
    private final MoIndicationMetadataProcessor moIndicationMetadataProcessor;

    public MoIndicationMetadataRoute(MoIndicationMetadataProcessor moIndicationMetadataProcessor) {
        this.moIndicationMetadataProcessor = moIndicationMetadataProcessor;

        setInput(direct(MO_INDICATION_F001_METADATA_ROUTE_ID));
        setOutput(direct(TO_DB_METADATA_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(MO_INDICATION_F001_METADATA_ROUTE_ID)

                .transform().message(moIndicationMetadataProcessor::metadataMessageStream)

                .to(getOutput());
    }
}
