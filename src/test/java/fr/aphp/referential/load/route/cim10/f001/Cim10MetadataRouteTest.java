package fr.aphp.referential.load.route.cim10.f001;

import java.net.URL;
import java.util.Date;

import org.apache.camel.Exchange;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.junit.Before;
import org.junit.Test;

import fr.aphp.referential.load.bean.MetadataBean;
import fr.aphp.referential.load.route.BaseRoute;
import fr.aphp.referential.load.route.BaseRouteTest;

import static fr.aphp.referential.load.util.CamelUtils.CIM10_F001_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.VALIDITY_DATE;

public class Cim10MetadataRouteTest extends BaseRouteTest {
    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        AdviceWithRouteBuilder.adviceWith(context(), CIM10_F001_ROUTE_ID, adviceWithRouteBuilder -> {
            adviceWithRouteBuilder
                    .weaveAddFirst()
                    .setHeader(VALIDITY_DATE, adviceWithRouteBuilder.constant(new Date()));
        });

        context().start();
    }

    @Override
    public boolean isUseAdviceWith() {
        return true;
    }

    @Override
    protected RoutesBuilder[] createRouteBuilders() throws Exception {
        URL resource = getClass().getClassLoader().getResource(".");
        String fileEndpoint = resourceIn("cim10") + "?noop=true";
        BaseRoute cim10Route = new Cim10Route()
                .setInput(fileEndpoint)
                .setOutput(IN);

        BaseRoute cim10F001MetadataRoute = new Cim10MetadataRoute()
                .setInput(IN)
                .setOutput(OUT);

        return new RoutesBuilder[]{
                cim10Route,
                cim10F001MetadataRoute
        };
    }

    @Test
    public void test() throws InterruptedException {
        // Expected
        out.expectedMessageCount(6);

        // Then
        assertMockEndpointsSatisfied();

        out.getExchanges().stream()
                .map(Exchange::getIn)
                .map(message -> message.getBody(MetadataBean.class))
                .forEach(this::asserts);
    }

    private void asserts(Object body) {
        MetadataBean conceptBean = assertIsInstanceOf(MetadataBean.class, body);
        assertEquals(conceptBean.standardConcept(), 1);
    }
}