package fr.aphp.referential.load.route.ghmghs.f002;

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

import static fr.aphp.referential.load.domain.type.SourceType.GHM;
import static fr.aphp.referential.load.util.CamelUtils.GHMGHS_F002_CONCEPT_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.SOURCE_TYPE;
import static fr.aphp.referential.load.util.CamelUtils.VALIDITY_DATE;

public class GhmGhsConceptRouteTest extends BaseRouteTest {
    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        AdviceWithRouteBuilder.adviceWith(context(), GHMGHS_F002_CONCEPT_ROUTE_ID, adviceWithRouteBuilder -> {
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
        String fileEndpoint = resourceIn("ghmghs") + "?noop=true&include=sup_pub.csv.f002_20200202";
        BaseRoute ghmGhsRoute = new GhmGhsRoute()
                .setInput(fileEndpoint)
                .setOutput(IN);

        BaseRoute ghmGhsConceptRoute = new GhmGhsConceptRoute()
                .setInput(IN)
                .setOutput(OUT);

        return new RoutesBuilder[]{
                ghmGhsRoute,
                ghmGhsConceptRoute
        };
    }

    @Test
    public void test() throws InterruptedException {
        // Expected
        out.expectedMessageCount(11);
        out.expectedHeaderReceived(SOURCE_TYPE, GHM);

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
        assertEquals(conceptBean.vocabularyId(), GHM);
        assertEquals(conceptBean.standardConcept(), 1);
    }
}