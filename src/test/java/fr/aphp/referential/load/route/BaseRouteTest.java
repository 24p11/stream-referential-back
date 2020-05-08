package fr.aphp.referential.load.route;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;

public class BaseRouteTest extends CamelTestSupport {
    protected static final String IN = "direct:in";
    protected static final String OUT = "mock:out";

    @Produce(value = IN)
    protected ProducerTemplate in;

    @EndpointInject(value = OUT)
    protected MockEndpoint out;

    protected String readFromResource(String path) throws URISyntaxException, IOException {
        return new String(readAllBytes(path));
    }

    protected byte[] readAllBytes(String path) throws URISyntaxException, IOException {
        return Files.readAllBytes(Paths.get(Objects.requireNonNull(getClass().getClassLoader()
                .getResource(path)).toURI()));
    }

    protected String anyMatch(String endpoint) {
        return ".*" + endpoint + ".*";
    }

    protected String direct(String uri) {
        return "direct:" + uri;
    }

    protected String resourceIn(String resourceDirectory) {
        return resource("in", resourceDirectory);
    }

    protected String resourceOut(String inOut, String resourceDirectory) {
        return resource("out", resourceDirectory);
    }

    private String resource(String inOut, String resourceDirectory) {
        URL resource = getClass().getClassLoader().getResource(".");
        return String.format("%sdata/%s/%s", resource, inOut, resourceDirectory);
    }
}
