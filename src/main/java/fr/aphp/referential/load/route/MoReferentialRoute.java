package fr.aphp.referential.load.route;

import org.springframework.stereotype.Component;

import static fr.aphp.referential.load.domain.type.MoReferentialFormatType.F001;
import static fr.aphp.referential.load.domain.type.SourceType.MO_REFERENTIAL;
import static fr.aphp.referential.load.util.CamelUtils.MO_REFERENTIAL_F001_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.MO_REFERENTIAL_ROUTE_ID;

@Component
public class MoReferentialRoute extends BaseRoute {
    public MoReferentialRoute() {
        setInput(direct(MO_REFERENTIAL));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(MO_REFERENTIAL_ROUTE_ID)

                .choice()
                .when(isFormat(F001)).to(direct(MO_REFERENTIAL_F001_ROUTE_ID));
    }
}
