package fr.aphp.referential.load.route.cim10;

import java.net.URL;

import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.junit.Before;
import org.junit.Test;

import fr.aphp.referential.load.bean.UpdateReferentialBean;
import fr.aphp.referential.load.message.Cim10V202004Message;
import fr.aphp.referential.load.route.BaseRouteTest;

import static fr.aphp.referential.load.domain.type.SourceType.CIM10;
import static fr.aphp.referential.load.util.CamelUtils.CIM10_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.UPDATE_REFERENTIAL_BEAN;

public class Cim10RouteTest extends BaseRouteTest {

    @Override
    @Before
    public void setUp() throws Exception {
        URL resource = getClass().getClassLoader().getResource(".");
        String fileEndpoint = resource + "data/in/cim10?noop=true";

        super.setUp();

        AdviceWithRouteBuilder.adviceWith(context(), CIM10_ROUTE_ID, adviceWithRouteBuilder -> {
            adviceWithRouteBuilder.replaceFromWith(fileEndpoint);

            adviceWithRouteBuilder
                    // Removing database interaction
                    .weaveByToUri(anyMatch("mybatis"))
                    .remove();
        });

        context().start();
    }

    @Override
    public boolean isUseAdviceWith() {
        return true;
    }

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new Cim10Route()
                .setOutput(OUT);
    }

    @Test
    public void test() throws InterruptedException {
        // Given
        UpdateReferentialBean updateReferentialBean = UpdateReferentialBean.of(CIM10);

        // Expected
        out.expectedMessageCount(2);
        out.expectedHeaderReceived(UPDATE_REFERENTIAL_BEAN, updateReferentialBean);

        // Then
        assertMockEndpointsSatisfied();

        assertIsInstanceOf(Cim10V202004Message.class, out.getExchanges().get(0).getIn().getBody());
    }
}