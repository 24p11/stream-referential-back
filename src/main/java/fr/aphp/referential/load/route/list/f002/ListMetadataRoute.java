package fr.aphp.referential.load.route.list.f002;

import org.springframework.stereotype.Component;

import fr.aphp.referential.load.processor.list.f002.ListMetadataProcessor;
import fr.aphp.referential.load.route.BaseRoute;

import static fr.aphp.referential.load.util.CamelUtils.LIST_F002_METADATA_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.TO_DB_METADATA_ROUTE_ID;

@Component(LIST_F002_METADATA_ROUTE_ID)
public class ListMetadataRoute extends BaseRoute {
    private final ListMetadataProcessor listMetadataProcessor;

    public ListMetadataRoute(ListMetadataProcessor listMetadataProcessor) {
        this.listMetadataProcessor = listMetadataProcessor;

        setInput(direct(LIST_F002_METADATA_ROUTE_ID));
        setOutput(direct(TO_DB_METADATA_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(LIST_F002_METADATA_ROUTE_ID)

                .transform().message(listMetadataProcessor::metadataMessageStream)

                .to(getOutput());
    }
}
