package fr.aphp.referential.load.route;

import org.springframework.stereotype.Component;

import static fr.aphp.referential.load.domain.type.Cim10FormatType.F001;
import static fr.aphp.referential.load.domain.type.SourceType.CIM10;
import static fr.aphp.referential.load.util.CamelUtils.CIM10_F001_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.CIM10_METADATA_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.CIM10_OUTPUT_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.CIM10_REFERENTIAL_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.CIM10_ROUTE_ID;

@Component
public class Cim10Route extends BaseRoute {
    public Cim10Route() {
        setInput(direct(CIM10));
        setOutputs(direct(CIM10_REFERENTIAL_ROUTE_ID), direct(CIM10_METADATA_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        // Input
        from(getInput())
                .routeId(CIM10_ROUTE_ID)

                .choice().when(isFormat(F001)).to(direct(CIM10_F001_ROUTE_ID));

        // Output concept & metadata
        from(direct(CIM10_OUTPUT_ROUTE_ID))
                .routeId(CIM10_OUTPUT_ROUTE_ID)

                .multicast()
                .stopOnException()
                .to(getOutputs())
                .end();

    }
}
