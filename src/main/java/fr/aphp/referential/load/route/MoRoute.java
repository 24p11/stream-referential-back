package fr.aphp.referential.load.route;

import org.springframework.stereotype.Component;

import static fr.aphp.referential.load.domain.type.MoFormatType.F001;
import static fr.aphp.referential.load.domain.type.SourceType.MO;
import static fr.aphp.referential.load.util.CamelUtils.MO_F001_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.MO_ROUTE_ID;

@Component
public class MoRoute extends BaseRoute {
    public MoRoute() {
        setInput(direct(MO));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(MO_ROUTE_ID)

                .choice()
                .when(isFormat(F001)).to(direct(MO_F001_ROUTE_ID));
    }
}
