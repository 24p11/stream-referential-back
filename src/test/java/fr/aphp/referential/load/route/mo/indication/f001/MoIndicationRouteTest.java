package fr.aphp.referential.load.route.mo.indication.f001;

import java.util.Collection;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.RoutesBuilder;
import org.junit.Test;

import fr.aphp.referential.load.message.mo.indication.f001.MoIndicationMessage;
import fr.aphp.referential.load.route.BaseRouteTest;

public class MoIndicationRouteTest extends BaseRouteTest {
    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        String fileEndpoint = resourceIn("mo-indication") + "?noop=true&include=referentiel_ucd_indication_les_complet_032020.csv.f001.20200202";
        return new MoIndicationRoute()
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
                .forEach(body -> assertIsInstanceOf(MoIndicationMessage.class, body));
    }
}