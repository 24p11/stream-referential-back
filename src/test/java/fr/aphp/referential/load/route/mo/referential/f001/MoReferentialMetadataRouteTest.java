package fr.aphp.referential.load.route.mo.referential.f001;

import java.util.stream.Stream;

import org.apache.camel.Exchange;
import org.apache.camel.RoutesBuilder;
import org.junit.Test;

import fr.aphp.referential.load.bean.MetadataBean;
import fr.aphp.referential.load.message.MetadataMessage;
import fr.aphp.referential.load.processor.mo.referential.f001.MoReferentialMetadataProcessor;
import fr.aphp.referential.load.route.BaseRoute;
import fr.aphp.referential.load.route.BaseRouteTest;

public class MoReferentialMetadataRouteTest extends BaseRouteTest {
    @Override
    protected RoutesBuilder[] createRouteBuilders() throws Exception {
        String fileEndpoint = resourceIn("mo-referential") + "?noop=true&include=historique_liste_ucd_en_sus032020.xls.F001_20201212";
        BaseRoute moReferentialRoute = new MoReferentialRoute()
                .setInput(fileEndpoint)
                .setOutput(IN);

        BaseRoute moReferentialMetadataRoute = new MoReferentialMetadataRoute(new MoReferentialMetadataProcessor())
                .setInput(IN)
                .setOutput(OUT);

        return new RoutesBuilder[]{
                moReferentialRoute,
                moReferentialMetadataRoute
        };
    }

    @Test
    public void test() throws InterruptedException {
        // Expected
        out.expectedMinimumMessageCount(6);

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
        if ("9436578".equals(metadataBean.conceptCode())) {
            assertEquals(metadataBean.startDate(), metadataBean.endDate());
        }
    }
}