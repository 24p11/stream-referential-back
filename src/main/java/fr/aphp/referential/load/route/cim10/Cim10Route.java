package fr.aphp.referential.load.route.cim10;

import java.nio.charset.StandardCharsets;

import org.apache.camel.model.dataformat.BindyType;
import org.springframework.stereotype.Component;

import fr.aphp.referential.load.route.BaseRoute;

import static fr.aphp.referential.load.domain.type.Cim10Type.V202004;
import static fr.aphp.referential.load.domain.type.SourceType.CIM10;
import static fr.aphp.referential.load.processor.Cim10Processor.forVersion;
import static fr.aphp.referential.load.util.CamelUtils.CIM10_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.CIM10_TO_DB_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.REFERENTIAL_TYPE;

@Component
public class Cim10Route extends BaseRoute {
    public Cim10Route() {
        setInput(direct(CIM10));
        setOutput(direct(CIM10_TO_DB_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(CIM10_ROUTE_ID)
                .setHeader(REFERENTIAL_TYPE, constant(CIM10))

                // Disable old entries if it fail later all rows will stay disabled
                .to(mybatis("updateEndDateReferential", "UpdateList", "inputHeader=" + REFERENTIAL_TYPE))

                .convertBodyTo(String.class, StandardCharsets.ISO_8859_1.displayName())
                .split().tokenize("\n").streaming()
                .unmarshal().bindy(BindyType.Csv, forVersion(V202004))
                .to(getOutput());
    }
}
