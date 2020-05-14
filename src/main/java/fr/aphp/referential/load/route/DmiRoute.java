package fr.aphp.referential.load.route;

import org.springframework.stereotype.Component;

import static fr.aphp.referential.load.domain.type.Cim10FormatType.F001;
import static fr.aphp.referential.load.domain.type.SourceType.DMI;
import static fr.aphp.referential.load.util.CamelUtils.DMI_F001_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.DMI_ROUTE_ID;

@Component
public class DmiRoute extends BaseRoute {
    public DmiRoute() {
        setInput(direct(DMI));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(DMI_ROUTE_ID)

                .choice()
                .when(isFormat(F001)).to(direct(DMI_F001_ROUTE_ID));
    }
}
