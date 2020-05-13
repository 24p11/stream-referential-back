package fr.aphp.referential.load.route.ghmghs.f001;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.camel.EndpointInject;
import org.apache.camel.Exchange;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.junit.Before;
import org.junit.Test;

import fr.aphp.referential.load.bean.ConceptBean;
import fr.aphp.referential.load.bean.ConceptRelationshipBean;
import fr.aphp.referential.load.domain.type.SourceType;
import fr.aphp.referential.load.route.BaseRoute;
import fr.aphp.referential.load.route.BaseRouteTest;

import static fr.aphp.referential.load.domain.type.SourceType.GHM;
import static fr.aphp.referential.load.domain.type.SourceType.GHS;
import static fr.aphp.referential.load.util.CamelUtils.GHMGHS_F001_CONCEPT_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.SOURCE_TYPE;
import static fr.aphp.referential.load.util.CamelUtils.TO_DB_CONCEPT_RELATIONSHIP_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.TO_DB_CONCEPT_ROUTE_ID;
import static java.util.stream.Collectors.groupingBy;

public class GhmGhsConceptRouteTest extends BaseRouteTest {
    @EndpointInject(value = "mock:direct:conceptRelationshipOut")
    private MockEndpoint conceptRelationshipOut;

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        AdviceWithRouteBuilder.adviceWith(context(), GHMGHS_F001_CONCEPT_ROUTE_ID, adviceWithRouteBuilder -> {
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
        String fileEndpoint = resourceIn("ghmghs") + "?noop=true&include=ghs_pub.csv.F001_20200530";
        BaseRoute ghmGhsRoute = new GhmGhsRoute()
                .setInput(fileEndpoint)
                .setOutput(IN);

        BaseRoute ghmGhsConceptRoute = new GhmGhsConceptRoute()
                .setInput(IN);

        return new RoutesBuilder[]{
                ghmGhsRoute,
                ghmGhsConceptRoute
        };
    }

    @Test
    public void testConceptBeanOutput() throws InterruptedException {
        // Expected
        out.expectedHeaderValuesReceivedInAnyOrder(SOURCE_TYPE, GHM, GHS);
        out.expectedMessageCount(4);

        // Then
        assertMockEndpointsSatisfied();

        Map<SourceType, List<ConceptBean>> conceptBeanBySourceTypeMap = out.getExchanges().stream()
                .map(Exchange::getIn)
                .map(message -> message.getBody(ConceptBean.class))
                .collect(groupingBy(ConceptBean::vocabularyId));

        assertEquals(conceptBeanBySourceTypeMap.get(GHM).size(), 2);
        assertEquals(conceptBeanBySourceTypeMap.get(GHS).size(), 2);

        conceptBeanBySourceTypeMap.values().stream()
                .flatMap(Collection::stream)
                .forEach(this::conceptBeanAsserts);
    }

    private void conceptBeanAsserts(Object body) {
        ConceptBean conceptBean = assertIsInstanceOf(ConceptBean.class, body);
        assertEquals(conceptBean.standardConcept(), 1);
    }

    @Test
    public void testConceptRelationshipBeanOutput() throws InterruptedException {
        // Expected
        conceptRelationshipOut.expectedMessageCount(2);
        conceptRelationshipOut.expectedBodiesReceivedInAnyOrder(
                ConceptRelationshipBean.of("GHM:01C031", "GHS:22"),
                ConceptRelationshipBean.of("GHM:01C032", "GHS:23")
        );

        // Then
        assertMockEndpointsSatisfied();

        conceptRelationshipOut.getExchanges().stream()
                .map(Exchange::getIn)
                .map(message -> message.getBody(ConceptRelationshipBean.class))
                .forEach(this::conceptBeanRelationshipAsserts);
    }

    private void conceptBeanRelationshipAsserts(Object body) {
        ConceptRelationshipBean conceptBean = assertIsInstanceOf(ConceptRelationshipBean.class, body);
    }
}