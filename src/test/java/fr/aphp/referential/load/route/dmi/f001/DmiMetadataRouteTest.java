package fr.aphp.referential.load.route.dmi.f001;

import java.util.stream.Stream;

import org.apache.camel.Exchange;
import org.apache.camel.RoutesBuilder;
import org.junit.Test;

import fr.aphp.referential.load.bean.MetadataBean;
import fr.aphp.referential.load.message.MetadataMessage;
import fr.aphp.referential.load.processor.dmi.f001.DmiMetadataProcessor;
import fr.aphp.referential.load.route.BaseRoute;
import fr.aphp.referential.load.route.BaseRouteTest;

public class DmiMetadataRouteTest extends BaseRouteTest {
    @Override
    protected RoutesBuilder[] createRouteBuilders() throws Exception {
        String fileEndpoint = resourceIn("dmi") + "?noop=true&include=historique_liste_lpp_en_sus032020.xls.F001_20200505";
        BaseRoute dmiRoute = new DmiRoute()
                .setInput(fileEndpoint)
                .setOutput(IN);

        BaseRoute dmiMetadataRoute = new DmiMetadataRoute(new DmiMetadataProcessor())
                .setInput(IN)
                .setOutput(OUT);

        return new RoutesBuilder[]{
                dmiRoute,
                dmiMetadataRoute
        };
    }

    @Test
    public void test() throws InterruptedException {
        // Expected
        out.expectedMinimumMessageCount(5);

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
        if ("3171593".equals(metadataBean.conceptCode())) {
            assertEquals(metadataBean.startDate(), metadataBean.endDate());
        }
    }
}