package fr.aphp.referential.load.route.cim10;

import java.nio.charset.StandardCharsets;

import org.apache.camel.model.dataformat.BindyType;
import org.springframework.stereotype.Component;

import fr.aphp.referential.load.message.Cim10202004Message;
import fr.aphp.referential.load.route.BaseRoute;

import static fr.aphp.referential.load.domain.type.Cim10Type.V202004;
import static fr.aphp.referential.load.util.CamelUtils.CIM10_202004_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.CIM10_TO_DB_ROUTE_ID;

@Component
public class Cim10202004Route extends BaseRoute {
    public Cim10202004Route() {
        setInput(direct(V202004));
        setOutput(direct(CIM10_TO_DB_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(CIM10_202004_ROUTE_ID)

                .convertBodyTo(String.class, StandardCharsets.ISO_8859_1.displayName())
                .split().tokenize("\n").streaming()
                .unmarshal().bindy(BindyType.Csv, Cim10202004Message.class)
                .to(getOutput());
    }
}
