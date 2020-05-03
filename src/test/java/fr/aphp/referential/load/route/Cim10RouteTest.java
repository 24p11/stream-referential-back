package fr.aphp.referential.load.route;

import java.net.URL;

import org.apache.camel.RoutesBuilder;
import org.junit.Test;

import fr.aphp.referential.load.message.Cim10V202004Message;

public class Cim10RouteTest extends BaseRouteTest {
    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        URL resource = getClass().getClassLoader().getResource(".");
        String fileEndpoint = resource + "data/in/cim10?noop=true";

        return new Cim10Route()
                .setInput(fileEndpoint)
                .setOutput(OUT);
    }

    @Test
    public void test() throws InterruptedException {
        // Expected
        out.expectedMessageCount(2);

        // Then
        assertMockEndpointsSatisfied();

        assertIsInstanceOf(Cim10V202004Message.class, out.getExchanges().get(0).getIn().getBody());
    }
}