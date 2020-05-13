package fr.aphp.referential.load.route;

import org.springframework.stereotype.Component;

import static fr.aphp.referential.load.domain.type.GhmGhsFormatType.F001;
import static fr.aphp.referential.load.domain.type.GhmGhsFormatType.F002;
import static fr.aphp.referential.load.domain.type.SourceType.GHMGHS;
import static fr.aphp.referential.load.util.CamelUtils.GHMGHS_F001_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.GHMGHS_F002_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.GHMGHS_ROUTE_ID;

@Component
public class GhmGhsRoute extends BaseRoute {
    public GhmGhsRoute() {
        setInput(direct(GHMGHS));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(GHMGHS_ROUTE_ID)

                .choice()
                .when(isFormat(F001)).to(direct(GHMGHS_F001_ROUTE_ID))
                .when(isFormat(F002)).to(direct(GHMGHS_F002_ROUTE_ID));
    }
}
