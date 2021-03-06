package fr.aphp.referential.load.route.ghmghs.f002;

import java.util.Collection;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.RoutesBuilder;
import org.junit.Test;

import fr.aphp.referential.load.message.ghmghs.f002.GhmGhsMessage;
import fr.aphp.referential.load.route.BaseRouteTest;

public class GhmGhsRouteTest extends BaseRouteTest {
    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        String fileEndpoint = resourceIn("ghmghs") + "?noop=true&include=sup_pub.csv.f002_20200202";
        return new GhmGhsRoute()
                .setInput(fileEndpoint)
                .setOutput(OUT);
    }

    @Test
    public void test() throws InterruptedException {
        // Expected
        out.expectedMessageCount(11);

        // Then
        assertMockEndpointsSatisfied();

        out.getExchanges().stream()
                .map(Exchange::getIn)
                .map(Message::getBody)
                .filter(body -> !(body instanceof Collection))
                .forEach(body -> assertIsInstanceOf(GhmGhsMessage.class, body));
    }
}