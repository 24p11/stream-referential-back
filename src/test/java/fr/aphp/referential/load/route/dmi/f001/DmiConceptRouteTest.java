package fr.aphp.referential.load.route.dmi.f001;

import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.RoutesBuilder;
import org.junit.Test;

import fr.aphp.referential.load.bean.ConceptBean;
import fr.aphp.referential.load.route.BaseRoute;
import fr.aphp.referential.load.route.BaseRouteTest;

public class DmiConceptRouteTest extends BaseRouteTest {
    @Override
    protected RoutesBuilder[] createRouteBuilders() throws Exception {
        String fileEndpoint = resourceIn("dmi") + "?noop=true&include=historique_liste_lpp_en_sus032020.xls.F001_20200505";
        BaseRoute dmiRoute = new DmiRoute()
                .setInput(fileEndpoint)
                .setOutput(IN);

        BaseRoute dmiConceptRoute = new DmiConceptRoute()
                .setInput(IN)
                .setOutput(OUT);

        return new RoutesBuilder[]{
                dmiRoute,
                dmiConceptRoute
        };
    }

    @Test
    public void test() throws InterruptedException {
        // Expected
        out.expectedMessageCount(5);

        // Then
        assertMockEndpointsSatisfied();

        out.getExchanges().stream()
                .map(Exchange::getIn)
                .map(message -> message.getBody(Optional.class))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(message -> assertIsInstanceOf(ConceptBean.class, message));
    }
}