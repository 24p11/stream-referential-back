package fr.aphp.referential.load.route.ccam.f003;

import java.util.Collection;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.RoutesBuilder;
import org.junit.Test;

import fr.aphp.referential.load.message.ccam.f002.CcamMessage;
import fr.aphp.referential.load.route.BaseRouteTest;
import fr.aphp.referential.load.route.ccam.f002.CcamRoute;

public class CcamRouteTest extends BaseRouteTest {
    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        String fileEndpoint = resourceIn("ccam") + "?noop=true&include=CCAM_EXT_DOC.txt.F002_20200401";
        return new CcamRoute()
                .setInput(fileEndpoint)
                .setOutput(OUT);
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
                .filter(body -> !(body instanceof Collection))
                .forEach(body -> assertIsInstanceOf(CcamMessage.class, body));
    }
}