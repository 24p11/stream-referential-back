package fr.aphp.referential.load.route.ccam.f001;

import java.util.Date;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.junit.Before;
import org.junit.Test;

import fr.aphp.referential.load.bean.ConceptBean;
import fr.aphp.referential.load.route.BaseRoute;
import fr.aphp.referential.load.route.BaseRouteTest;

import static fr.aphp.referential.load.util.CamelUtils.CCAM_F001_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.VALIDITY_DATE;

public class CcamConceptRouteTest extends BaseRouteTest {
    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        AdviceWithRouteBuilder.adviceWith(context(), CCAM_F001_ROUTE_ID, adviceWithRouteBuilder -> {
            adviceWithRouteBuilder
                    .weaveAddFirst()
                    .setHeader(VALIDITY_DATE, adviceWithRouteBuilder.constant(new Date()));
        });

        context().start();
    }

    @Override
    public boolean isUseAdviceWith() {
        return true;
    }

    @Override
    protected RoutesBuilder[] createRouteBuilders() throws Exception {
        String fileEndpoint = resourceIn("ccam") + "?noop=true&include=fichier_complementaire_ccam_descriptive_a_usage_pmsi_2020_v3.xlsx.F001_20200401";
        BaseRoute ccamRoute = new CcamRoute()
                .setInput(fileEndpoint)
                .setOutput(IN);

        BaseRoute ccamConceptRoute = new CcamConceptRoute()
                .setInput(IN)
                .setOutput(OUT);

        return new RoutesBuilder[]{
                ccamRoute,
                ccamConceptRoute
        };
    }

    @Test
    public void test() throws InterruptedException {
        // Expected
        out.expectedMinimumMessageCount(1);

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