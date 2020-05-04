package fr.aphp.referential.load.route.cim10;

import java.net.URL;
import java.util.Date;

import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.BindyType;
import org.junit.Test;

import fr.aphp.referential.load.bean.MetadataBean;
import fr.aphp.referential.load.message.cim10.Cim10F001Message;
import fr.aphp.referential.load.route.BaseRoute;
import fr.aphp.referential.load.route.BaseRouteTest;

import static fr.aphp.referential.load.util.CamelUtils.VALIDITY_DATE;

public class Cim10F001MetadataRouteTest extends BaseRouteTest {

    @Override
    protected RoutesBuilder[] createRouteBuilders() throws Exception {
        RouteBuilder beforeRoute = new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                URL resource = getClass().getClassLoader().getResource(".");
                String fileEndpoint = resource + "data/in/cim10?noop=true";

                from(fileEndpoint)
                        .setHeader(VALIDITY_DATE, constant(new Date()))
                        .unmarshal().bindy(BindyType.Csv, Cim10F001Message.class)
                        .split(body())
                        .to(IN);
            }
        };

        BaseRoute cim10F001MetadataRoute = new Cim10F001MetadataRoute()
                .setInput(IN)
                .setOutput(OUT);

        return new RoutesBuilder[]{
                beforeRoute,
                cim10F001MetadataRoute
        };
    }

    @Test
    public void test() throws InterruptedException {
        // Expected
        out.expectedMessageCount(6);

        // Then
        assertMockEndpointsSatisfied();

        assertIsInstanceOf(MetadataBean.class, out.getExchanges().get(0).getIn().getBody());
    }
}