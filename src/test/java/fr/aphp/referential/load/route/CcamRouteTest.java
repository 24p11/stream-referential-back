package fr.aphp.referential.load.route;

import java.net.URL;
import java.util.Date;
import java.util.Optional;

import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.junit.Before;
import org.junit.Test;

import fr.aphp.referential.load.message.ccam.f001.CcamMessage;

import static fr.aphp.referential.load.util.CamelUtils.CCAM_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.VALIDITY_DATE;

public class CcamRouteTest extends BaseRouteTest {

    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        AdviceWithRouteBuilder.adviceWith(context(), CCAM_ROUTE_ID, adviceWithRouteBuilder -> {
            adviceWithRouteBuilder.weaveAddFirst()
                    .setHeader(VALIDITY_DATE, adviceWithRouteBuilder.constant(new Date()));
        });

        context().start();
    }

    @Override
    public boolean isUseAdviceWith() {
        return true;
    }

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        URL resource = getClass().getClassLoader().getResource(".");
        String fileEndpoint = resource + "data/in/ccam?noop=true";

        return new CcamRoute()
                .setInput(fileEndpoint)
                .setOutputs(OUT);
    }

    @Test
    public void test() throws InterruptedException {

        // Expected
        // Not really one but for fast test
        out.expectedMessageCount(1);

        // Then
        assertMockEndpointsSatisfied();

        assertIsInstanceOf(CcamMessage.class, out.getExchanges().get(0).getIn().getBody(Optional.class).get());
    }
}