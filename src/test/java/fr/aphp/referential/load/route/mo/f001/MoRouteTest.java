package fr.aphp.referential.load.route.mo.f001;

import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.junit.Before;
import org.junit.Test;

import fr.aphp.referential.load.domain.type.mo.MoEventType;
import fr.aphp.referential.load.message.mo.f001.MoMessage;
import fr.aphp.referential.load.route.BaseRouteTest;

import static fr.aphp.referential.load.util.CamelUtils.MO_F001_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.VALIDITY_DATE;

public class MoRouteTest extends BaseRouteTest {
    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        AdviceWithRouteBuilder.adviceWith(context(), MO_F001_ROUTE_ID, adviceWithRouteBuilder -> {
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
    protected RoutesBuilder createRouteBuilder() throws Exception {
        String fileEndpoint = resourceIn("mo") + "?noop=true&include=historique_liste_ucd_en_sus032020.xls.F001_20201212";
        return new MoRoute()
                .setInput(fileEndpoint)
                .setOutput(OUT);
    }

    @Test
    public void test() throws InterruptedException, ParseException {
        // Expected
        out.expectedMessageCount(6);

        // Then
        assertMockEndpointsSatisfied();

        out.getExchanges().stream()
                .map(Exchange::getIn)
                .map(message -> message.getBody(Optional.class))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(this::asserts);
    }

    private void asserts(Object body) {
        MoMessage moMessage = assertIsInstanceOf(MoMessage.class, body);

        if ("9436578".equals(moMessage.ucd7())) {
            assertEquals(MoEventType.DELETE, moMessage.moEventType());
        } else if ("9258504".equals(moMessage.ucd7())) {
            assertEquals(MoEventType.REGISTER, moMessage.moEventType());
        } else {
            assertEquals(MoEventType.MODIFY, moMessage.moEventType());
        }
    }
}