package fr.aphp.referential.load.route.dmi.f001;

import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.junit.Before;
import org.junit.Test;

import fr.aphp.referential.load.domain.type.dmi.DmiEventType;
import fr.aphp.referential.load.message.dmi.f001.DmiMessage;
import fr.aphp.referential.load.route.BaseRouteTest;

import static fr.aphp.referential.load.util.CamelUtils.DMI_F001_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.VALIDITY_DATE;

public class DmiRouteTest extends BaseRouteTest {
    @Override
    @Before
    public void setUp() throws Exception {
        super.setUp();

        AdviceWithRouteBuilder.adviceWith(context(), DMI_F001_ROUTE_ID, adviceWithRouteBuilder -> {
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
        String fileEndpoint = resourceIn("dmi") + "?noop=true&include=historique_liste_lpp_en_sus032020.xls.F001_20200505";
        return new DmiRoute()
                .setInput(fileEndpoint)
                .setOutput(OUT);
    }

    @Test
    public void test() throws InterruptedException, ParseException {
        // Expected
        out.expectedMessageCount(5);

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
        DmiMessage dmiMessage = assertIsInstanceOf(DmiMessage.class, body);

        if ("3171593".equals(dmiMessage.lpp())) {
            assertEquals(DmiEventType.DELETE, dmiMessage.dmiEventType());
        } else {
            assertEquals(DmiEventType.REGISTER, dmiMessage.dmiEventType());
        }
    }
}