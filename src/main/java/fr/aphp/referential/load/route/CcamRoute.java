package fr.aphp.referential.load.route;

import org.springframework.stereotype.Component;

import static fr.aphp.referential.load.domain.type.CcamFormatType.F001;
import static fr.aphp.referential.load.domain.type.CcamFormatType.F002;
import static fr.aphp.referential.load.domain.type.SourceType.CCAM;
import static fr.aphp.referential.load.util.CamelUtils.CCAM_F001_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.CCAM_F002_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.CCAM_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.FORMAT;

@Component
public class CcamRoute extends BaseRoute {

    public CcamRoute() {
        setInput(direct(CCAM));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(CCAM_ROUTE_ID)

                .choice()
                .when(isFormat(F001)).setHeader(FORMAT, constant(F001)).to(direct(CCAM_F001_ROUTE_ID))
                .when(isFormat(F002)).setHeader(FORMAT, constant(F002)).to(direct(CCAM_F002_ROUTE_ID));
    }
}
