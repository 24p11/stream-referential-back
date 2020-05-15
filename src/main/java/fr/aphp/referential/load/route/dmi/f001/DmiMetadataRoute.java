package fr.aphp.referential.load.route.dmi.f001;

import org.springframework.stereotype.Component;

import fr.aphp.referential.load.processor.dmi.f001.DmiMetadataProcessor;
import fr.aphp.referential.load.route.BaseRoute;

import static fr.aphp.referential.load.util.CamelUtils.DMI_F001_METADATA_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.TO_DB_METADATA_ROUTE_ID;

@Component(DMI_F001_METADATA_ROUTE_ID)
public class DmiMetadataRoute extends BaseRoute {
    private final DmiMetadataProcessor dmiMetadataProcessor;

    public DmiMetadataRoute(DmiMetadataProcessor dmiMetadataProcessor) {
        this.dmiMetadataProcessor = dmiMetadataProcessor;

        setInput(direct(DMI_F001_METADATA_ROUTE_ID));
        setOutput(direct(TO_DB_METADATA_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(DMI_F001_METADATA_ROUTE_ID)

                .transform().message(dmiMetadataProcessor::metadataMessageStream)

                .to(getOutput());
    }
}
