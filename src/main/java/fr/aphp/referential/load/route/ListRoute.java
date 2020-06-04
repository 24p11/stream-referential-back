package fr.aphp.referential.load.route;

import org.springframework.stereotype.Component;

import static fr.aphp.referential.load.domain.type.ListFormatType.F001;
import static fr.aphp.referential.load.domain.type.ListFormatType.F002;
import static fr.aphp.referential.load.domain.type.SourceType.LIST;
import static fr.aphp.referential.load.util.CamelUtils.LIST_F001_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.LIST_F002_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.LIST_ROUTE_ID;

@Component
public class ListRoute extends BaseRoute {
    public ListRoute() {
        setInput(direct(LIST));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(LIST_ROUTE_ID)

                .choice()
                .when(isFormat(F001)).to(direct(LIST_F001_ROUTE_ID))
                .when(isFormat(F002)).to(direct(LIST_F002_ROUTE_ID));
    }
}
