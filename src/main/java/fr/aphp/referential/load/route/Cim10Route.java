package fr.aphp.referential.load.route;

import org.springframework.stereotype.Component;

import fr.aphp.referential.load.domain.type.CcamFormatType;

import static fr.aphp.referential.load.domain.type.Cim10FormatType.F001;
import static fr.aphp.referential.load.domain.type.SourceType.CIM10;
import static fr.aphp.referential.load.util.CamelUtils.CIM10_F001_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.CIM10_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.FORMAT;

@Component
public class Cim10Route extends BaseRoute {
    public Cim10Route() {
        setInput(direct(CIM10));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(CIM10_ROUTE_ID)

                .choice()
                .when(isFormat(F001)).setHeader(FORMAT, constant(CcamFormatType.F001)).to(direct(CIM10_F001_ROUTE_ID));
    }
}
