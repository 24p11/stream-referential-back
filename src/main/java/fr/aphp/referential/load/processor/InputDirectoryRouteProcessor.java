package fr.aphp.referential.load.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.spi.Language;

public class InputDirectoryRouteProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws RuntimeException {
        Language simple = exchange.getContext().resolveLanguage("simple");

        if (hasNoValidExtension(simple, exchange)) {
            throw new RuntimeException(String.format("Invalid file extension '%s'", fileExtension(simple, exchange)));
        }
    }

    /**
     * Processed file must have a valid extension representing version
     * V202004 or v202105 is valid extension
     */
    private static boolean hasNoValidExtension(Language simple, Exchange exchange) {
        return simple.createPredicate("${file:name.ext.single} !regex '[v|V][0-9]{6}'")
                .matches(exchange);
    }

    private static String fileExtension(Language simple, Exchange exchange) {
        return simple.createExpression("${file:name.ext.single}")
                .evaluate(exchange, String.class);
    }
}
