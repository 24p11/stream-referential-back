package fr.aphp.referential.load.route.list.f001;

import java.util.stream.Stream;

import org.apache.camel.Exchange;
import org.apache.camel.RoutesBuilder;
import org.junit.Test;

import fr.aphp.referential.load.message.MetadataMessage;
import fr.aphp.referential.load.processor.list.f001.ListMetadataProcessor;
import fr.aphp.referential.load.route.BaseRouteTest;

public class ListMetadataRouteTest extends BaseRouteTest {
    @Override
    protected RoutesBuilder[] createRouteBuilders() throws Exception {
        String fileEndpoint = resourceIn("list") + "?noop=true&include=list.csv.F001_20200601";
        var listRoute = new ListRoute()
                .setInput(fileEndpoint)
                .setOutput(IN);
        var listMetadataRoute = new ListMetadataRoute(new ListMetadataProcessor())
                .setInput(IN)
                .setOutput(OUT);

        return new RoutesBuilder[]{
                listRoute,
                listMetadataRoute
        };
    }

    @Test
    public void test() throws InterruptedException {
        // Expected
        out.expectedMinimumMessageCount(2);

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
        assertEquals(0, metadataMessage.metadataBean().standardConcept());
    }
}