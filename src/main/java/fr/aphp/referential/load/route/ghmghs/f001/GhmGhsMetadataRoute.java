package fr.aphp.referential.load.route.ghmghs.f001;

import fr.aphp.referential.load.route.BaseRoute;

import static fr.aphp.referential.load.util.CamelUtils.GHMGHS_F001_METADATA_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.TO_DB_METADATA_ROUTE_ID;

//@Component(GHMGHS_F001_METADATA_ROUTE_ID)
public class GhmGhsMetadataRoute extends BaseRoute {
    public GhmGhsMetadataRoute() {
        setInput(direct(GHMGHS_F001_METADATA_ROUTE_ID));
        setOutput(direct(TO_DB_METADATA_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(GHMGHS_F001_METADATA_ROUTE_ID)

                //.to(getOutput());

                .process();
    }
}
