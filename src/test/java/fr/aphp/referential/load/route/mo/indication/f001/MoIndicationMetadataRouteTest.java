package fr.aphp.referential.load.route.mo.indication.f001;

import java.util.stream.Stream;

import org.apache.camel.Exchange;
import org.apache.camel.RoutesBuilder;
import org.junit.Test;

import fr.aphp.referential.load.bean.MetadataBean;
import fr.aphp.referential.load.message.MetadataMessage;
import fr.aphp.referential.load.processor.mo.indication.f001.MoIndicationMetadataProcessor;
import fr.aphp.referential.load.route.BaseRoute;
import fr.aphp.referential.load.route.BaseRouteTest;

public class MoIndicationMetadataRouteTest extends BaseRouteTest {
    @Override
    protected RoutesBuilder[] createRouteBuilders() throws Exception {
        String fileEndpoint = resourceIn("mo-indication") + "?noop=true&include=referentiel_ucd_indication_les_complet_032020.csv.f001.20200202";
        BaseRoute moIndicationRoute = new MoIndicationRoute()
                .setInput(fileEndpoint)
                .setOutput(IN);

        BaseRoute moIndicationMetadataRoute = new MoIndicationMetadataRoute(new MoIndicationMetadataProcessor())
                .setInput(IN)
                .setOutput(OUT);

        return new RoutesBuilder[]{
                moIndicationRoute,
                moIndicationMetadataRoute
        };
    }

    @Test
    public void test() throws InterruptedException {
        // Expected
        out.expectedMinimumMessageCount(4);

        // Then
        assertMockEndpointsSatisfied();

        //noinspection unchecked
        out.getExchanges().stream()
                .map(Exchange::getIn)
                .flatMap(message -> message.getBody(Stream.class))
                .forEach(this::asserts);
    }

    private void asserts(Object body) {
        MetadataMessage metadataMessage = assertIsInstanceOf(MetadataMessage.class, body);

        MetadataBean metadataBean = metadataMessage.metadataBean();
        assertEquals(1, metadataBean.standardConcept());
        if ("9439944".equals(metadataBean.conceptCode())) {
            assertNotNull(metadataBean.endDate());
        }
    }
}