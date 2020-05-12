package fr.aphp.referential.load.route.ghmghs.f001;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.camel.Exchange;
import org.apache.camel.RoutesBuilder;
import org.junit.Test;

import fr.aphp.referential.load.bean.ConceptBean;
import fr.aphp.referential.load.domain.type.SourceType;
import fr.aphp.referential.load.route.BaseRoute;
import fr.aphp.referential.load.route.BaseRouteTest;

public class GhmGhsConceptRouteTest extends BaseRouteTest {
    @Override
    protected RoutesBuilder[] createRouteBuilders() throws Exception {
        String fileEndpoint = resourceIn("ghmghs") + "?noop=true&include=ghs_pub.csv.F001_20200530";
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
        out.expectedMessageCount(4);

        // Then
        assertMockEndpointsSatisfied();

        Map<SourceType, List<ConceptBean>> conceptBeanBySourceTypeMap = out.getExchanges().stream()
                .map(Exchange::getIn)
                .map(message -> message.getBody(ConceptBean.class))
                .collect(Collectors.groupingBy(ConceptBean::vocabularyId));

        assertEquals(conceptBeanBySourceTypeMap.get(SourceType.GHM).size(), 2);
        assertEquals(conceptBeanBySourceTypeMap.get(SourceType.GHS).size(), 2);

        conceptBeanBySourceTypeMap.values().stream()
                .flatMap(Collection::stream)
                .forEach(this::asserts);
    }

    private void asserts(Object body) {
        ConceptBean conceptBean = assertIsInstanceOf(ConceptBean.class, body);

        assertEquals(conceptBean.standardConcept(), 1);
    }
}