package fr.aphp.referential.load.route.mo.referential.f001;

import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.RoutesBuilder;
import org.junit.Test;

import fr.aphp.referential.load.bean.ConceptBean;
import fr.aphp.referential.load.route.BaseRoute;
import fr.aphp.referential.load.route.BaseRouteTest;

public class MoConceptRouteTest extends BaseRouteTest {
    @Override
    protected RoutesBuilder[] createRouteBuilders() throws Exception {
        String fileEndpoint = resourceIn("mo-referential") + "?noop=true&include=historique_liste_ucd_en_sus032020.xls.F001_20201212";
        BaseRoute moRoute = new MoReferentialRoute()
                .setInput(fileEndpoint)
                .setOutput(IN);

        BaseRoute moConceptRoute = new MoReferentialConceptRoute()
                .setInput(IN)
                .setOutput(OUT);

        return new RoutesBuilder[]{
                moRoute,
                moConceptRoute
        };
    }

    @Test
    public void test() throws InterruptedException {
        // Expected
        out.expectedMessageCount(6);

        // Then
        assertMockEndpointsSatisfied();

        out.getExchanges().stream()
                .map(Exchange::getIn)
                .map(message -> message.getBody(Optional.class))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(this::asserts);
    }

    private void asserts(Object body) {
        ConceptBean conceptBean = assertIsInstanceOf(ConceptBean.class, body);
        assertEquals(1, conceptBean.standardConcept());
    }
}