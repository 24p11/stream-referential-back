package fr.aphp.referential.load.route.mo.referential.f001;

import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.junit.Before;
import org.junit.Test;

import fr.aphp.referential.load.domain.type.mo.referential.MoReferentialEventType;
import fr.aphp.referential.load.message.mo.referential.f001.MoReferentialMessage;
import fr.aphp.referential.load.route.BaseRouteTest;

import static fr.aphp.referential.load.util.CamelUtils.MO_REFERENTIAL_F001_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.VALIDITY_DATE;

public class MoReferentialRouteTest extends BaseRouteTest {
    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        AdviceWithRouteBuilder.adviceWith(context(), MO_REFERENTIAL_F001_ROUTE_ID, adviceWithRouteBuilder -> {
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
        String fileEndpoint = resourceIn("mo-referential") + "?noop=true&include=historique_liste_ucd_en_sus032020.xls.F001_20201212";
        return new MoReferentialRoute()
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
        MoReferentialMessage moReferentialMessage = assertIsInstanceOf(MoReferentialMessage.class, body);

        if ("9436578".equals(moReferentialMessage.ucd7())) {
            assertEquals(MoReferentialEventType.DELETE, moReferentialMessage.moEventType());
        } else if ("9258504".equals(moReferentialMessage.ucd7())) {
            assertEquals(MoReferentialEventType.REGISTER, moReferentialMessage.moEventType());
        } else {
            assertEquals(MoReferentialEventType.MODIFY, moReferentialMessage.moEventType());
        }
    }
}