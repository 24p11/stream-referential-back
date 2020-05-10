package fr.aphp.referential.load.processor;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

import org.apache.camel.CamelExecutionException;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.junit.Test;
import org.springframework.util.Assert;

import fr.aphp.referential.load.route.BaseRouteTest;
import io.vavr.control.Try;

import static fr.aphp.referential.load.util.CamelUtils.FORMAT;
import static fr.aphp.referential.load.util.CamelUtils.VALIDITY_DATE;

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
    public void testValidFileExtension() throws InterruptedException {
        // When
        in.sendBody(file("LIBCIM10MULTI.TXT.f001_20200401"));

        // Expected
        // Reuse camel assertions (expectedHeaderReceived) when it will be possible https://issues.apache.org/jira/browse/CAMEL-15037
        out.expectedMessageCount(1);

        // Then
        assertMockEndpointsSatisfied();

        out.getExchanges().stream()
                .map(Exchange::getIn)
                .map(Message::getHeaders)
                .forEach(headers -> Try.run(() -> asserts(headers)).get());
    }

    private void asserts(Map<String, Object> headers) throws ParseException {
        assertEquals("F001", headers.get(FORMAT));
        assertEquals(new SimpleDateFormat("yyyyMMdd").parse("20200401"), headers.get(VALIDITY_DATE));
    }

    @Test
    public void testInvalidFileExtension() throws InterruptedException {
        // When
        try {
            in.sendBody(file("LIBCIM10MULTI.TXT.f01_2020"));
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