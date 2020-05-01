package fr.aphp.referential.load.processor;

import java.net.URISyntaxException;
import java.net.URL;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.junit.Test;
import org.springframework.util.Assert;

import fr.aphp.referential.load.route.BaseRouteTest;

public class InputDirectoryRouteProcessorTest extends BaseRouteTest {

    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from(IN)
                        .pollEnrich().simple("${body}")
                        .process(new InputDirectoryRouteProcessor())
                        .to(OUT);
            }
        };
    }

    @Test
    public void testValidFileExtension() throws InterruptedException, URISyntaxException {
        // When
        in.sendBody(file("LIBCIM10MULTI.TXT.v202004"));

        // Then
        out.expectedMessageCount(1);
        assertMockEndpointsSatisfied();
    }

    @Test
    public void testInvalidFileExtension() throws InterruptedException, URISyntaxException {
        // When
        try {
            in.sendBody(file("LIBCIM10MULTI.TXT.v2020"));
            fail("Should throw and exception du to invalid file extension");
        } catch (CamelExecutionException e) {
            Assert.isInstanceOf(RuntimeException.class, e.getCause());
        }

        // Then
        out.expectedMessageCount(0);
        assertMockEndpointsSatisfied();
    }

    private String file(String filename) {
        URL resource = getClass().getClassLoader().getResource(".");
        return resource + "data/in/cim10/processor?noop=true&filename=" + filename;
    }
}