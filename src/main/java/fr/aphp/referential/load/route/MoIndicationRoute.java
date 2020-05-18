package fr.aphp.referential.load.route;

import org.springframework.stereotype.Component;

import static fr.aphp.referential.load.domain.type.MoReferentialFormatType.F001;
import static fr.aphp.referential.load.util.CamelUtils.MO_INDICATION_F001_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.MO_INDICATION_ROUTE_ID;

@Component
public class MoIndicationRoute extends BaseRoute {
    public MoIndicationRoute() {
        setInput(direct(MO_INDICATION_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(MO_INDICATION_ROUTE_ID)

                .choice()
                .when(isFormat(F001)).to(direct(MO_INDICATION_F001_ROUTE_ID));
    }
}
