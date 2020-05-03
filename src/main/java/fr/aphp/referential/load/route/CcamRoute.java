package fr.aphp.referential.load.route;

import java.io.File;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import fr.aphp.referential.load.processor.Ccam202004Processor;

import static fr.aphp.referential.load.domain.type.Cim10Type.V202004;
import static fr.aphp.referential.load.domain.type.SourceType.CCAM;
import static fr.aphp.referential.load.util.CamelUtils.CCAM_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.TO_DB_REFERENTIAL_ROUTE_ID;

@Component
public class CcamRoute extends BaseRoute {

    public CcamRoute() {
        setInput(direct(CCAM));
        setOutput(direct(TO_DB_REFERENTIAL_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(CCAM_ROUTE_ID)

                .choice()
                // V202004
                .when(isVersion(V202004))

                .transform().body(File.class, Ccam202004Processor::xlsRows)

                .split(body())

                .filter(Ccam202004Processor::isValidRow)
                .transform().body(Row.class, Ccam202004Processor::referentialBean)

                .to(getOutput());
    }
}
