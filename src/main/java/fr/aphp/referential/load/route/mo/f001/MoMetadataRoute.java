package fr.aphp.referential.load.route.mo.f001;

import org.springframework.stereotype.Component;

import fr.aphp.referential.load.route.BaseRoute;

import static fr.aphp.referential.load.util.CamelUtils.MO_F001_METADATA_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.TO_DB_METADATA_ROUTE_ID;

@Component(MO_F001_METADATA_ROUTE_ID)
public class MoMetadataRoute extends BaseRoute {
    public MoMetadataRoute() {
        setInput(direct(MO_F001_METADATA_ROUTE_ID));
        setOutput(direct(TO_DB_METADATA_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(MO_F001_METADATA_ROUTE_ID)

                .to(getOutput());
    }
}
