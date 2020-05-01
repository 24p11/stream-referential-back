package fr.aphp.referential.load.route.ccam;

import java.io.File;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Component;

import fr.aphp.referential.load.processor.CcamProcessor;
import fr.aphp.referential.load.route.BaseRoute;

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

                .transform().body(File.class, CcamProcessor::xlsRows)
                .split(body())

                .filter(CcamProcessor::isValidRow)

                .transform().body(Row.class, CcamProcessor::referentialBean)

                .to(getOutput())

        ;
    }
}
