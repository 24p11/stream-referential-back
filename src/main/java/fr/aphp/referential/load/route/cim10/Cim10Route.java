package fr.aphp.referential.load.route.cim10;

import org.apache.camel.Predicate;
import org.springframework.stereotype.Component;

import fr.aphp.referential.load.domain.type.Cim10Type;
import fr.aphp.referential.load.route.BaseRoute;

import static fr.aphp.referential.load.domain.type.Cim10Type.V042020;
import static fr.aphp.referential.load.domain.type.SourceType.CIM10;
import static fr.aphp.referential.load.util.CamelUtils.CIM10_ROUTE_ID;
import static java.lang.String.format;

@Component
public class Cim10Route extends BaseRoute {
    public Cim10Route() {
        setInput(route(CIM10));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        from(getInput())
                .routeId(CIM10_ROUTE_ID)

                .choice()
                .when(isVersion(V042020)).process();
    }

    private Predicate isVersion(Cim10Type cim10Type) {
        return simple(format("${file:name.ext.single} =~ '%s'", V042020));
    }
}
