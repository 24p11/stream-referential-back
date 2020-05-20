package fr.aphp.referential.load.route.mo.indication.f001;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Before;
import org.junit.Test;

import fr.aphp.referential.load.bean.ConceptBean;
import fr.aphp.referential.load.bean.ConceptRelationshipBean;
import fr.aphp.referential.load.route.BaseRoute;
import fr.aphp.referential.load.route.BaseRouteTest;

import static fr.aphp.referential.load.util.CamelUtils.MO_INDICATION_F001_CONCEPT_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.TO_DB_CONCEPT_RELATIONSHIP_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.TO_DB_CONCEPT_ROUTE_ID;

public class MoIndicationConceptRouteTest extends BaseRouteTest {
    @EndpointInject(value = "mock:direct:conceptRelationshipOut")
    private MockEndpoint conceptRelationshipOut;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        AdviceWithRouteBuilder.adviceWith(context(), MO_INDICATION_F001_CONCEPT_ROUTE_ID, adviceWithRouteBuilder -> {
            adviceWithRouteBuilder
                    .weaveByToUri(direct(TO_DB_CONCEPT_ROUTE_ID))
                    .replace()
                    .to(OUT);

            adviceWithRouteBuilder
                    .weaveByToUri(direct(TO_DB_CONCEPT_RELATIONSHIP_ROUTE_ID))
                    .replace()
                    .to(conceptRelationshipOut);
        });

        context().start();
    }

    @Override
    public boolean isUseAdviceWith() {
        return true;
    }

    @Override
    protected RoutesBuilder[] createRouteBuilders() throws Exception {
        String fileEndpoint = resourceIn("mo-indication") + "?noop=true&include=referentiel_ucd_indication_les_complet_032020.csv.f001.20200202";
        BaseRoute moIndicationRoute = new MoIndicationRoute()
                .setInput(fileEndpoint)
                .setOutput(IN);

        BaseRoute moIndicationConceptRoute = new MoIndicationConceptRoute()
                .setInput(IN)
                .setOutput(OUT);

        return new RoutesBuilder[]{
                moIndicationRoute,
                moIndicationConceptRoute
        };
    }

    @Test
    public void test() throws InterruptedException {
        // Expected
        out.expectedMessageCount(3);

        // Then
        assertMockEndpointsSatisfied();

        out.getExchanges().stream()
                .map(Exchange::getIn)
                .map(Message::getBody)
                .forEach(this::asserts);
    }

    private void asserts(Object body) {
        ConceptBean conceptBean = assertIsInstanceOf(ConceptBean.class, body);
        assertEquals(1, conceptBean.standardConcept());
    }

    @Test
    public void testConceptRelationshipBeanOutput() throws InterruptedException {
        // Expected
        conceptRelationshipOut.expectedMessageCount(3);
        conceptRelationshipOut.expectedBodiesReceivedInAnyOrder(
                ConceptRelationshipBean.of("MO_REFERENTIAL:9196246", "MO_INDICATION:9196246"),
                ConceptRelationshipBean.of("MO_REFERENTIAL:9439944", "MO_INDICATION:9439944")
        );

        // Then
        assertMockEndpointsSatisfied();

        conceptRelationshipOut.getExchanges().stream()
                .map(Exchange::getIn)
                .map(message -> message.getBody(ConceptRelationshipBean.class))
                .forEach(this::conceptBeanRelationshipAsserts);
    }

    private void conceptBeanRelationshipAsserts(Object body) {
        assertIsInstanceOf(ConceptRelationshipBean.class, body);
    }
}