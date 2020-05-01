package fr.aphp.referential.load.route.ccam;

import java.net.URL;

import org.apache.camel.RoutesBuilder;
import org.junit.Test;

import fr.aphp.referential.load.route.BaseRouteTest;

public class CcamRouteTest extends BaseRouteTest {

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        URL resource = getClass().getClassLoader().getResource(".");
        String fileEndpoint = resource + "data/in/ccam?noop=true";

        return new CcamRoute()
                .setInput(fileEndpoint)
                .setOutput(OUT);
    }

    @Test
    public void test() throws InterruptedException {

        // Expected
        out.expectedMessageCount(1);

        // Then
        assertMockEndpointsSatisfied();
    }
}