package fr.aphp.referential.load.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.spi.Language;

import static fr.aphp.referential.load.util.CamelUtils.FILE_EXT_SEPARATOR;
import static fr.aphp.referential.load.util.CamelUtils.VALIDITY_DATE;
import static org.apache.camel.Exchange.FILE_NAME;

public class InputDirectoryRouteProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws RuntimeException {
        Language simple = exchange.getContext().resolveLanguage("simple");

        if (hasNoValidExtension(simple, exchange)) {
            throw new RuntimeException(String.format("Invalid file extension '%s'", fileExtension(simple, exchange)));
        }

        // Set headers
        Message message = exchange.getIn();
        String filename = message.getHeader(FILE_NAME, String.class);
        message.setHeader(VALIDITY_DATE, filename.split(FILE_EXT_SEPARATOR)[1]);
    }

    /**
     * Processed file must have a valid extension representing format and validity date
     * f001_20100401 or F001_20100401 is valid extension
     */
    private static boolean hasNoValidExtension(Language simple, Exchange exchange) {
        return simple.createPredicate("${file:name.ext.single} !regex '.*[f|F][0-9]{3}_[0-9]{8}'")
                .matches(exchange);
    }

    private static String fileExtension(Language simple, Exchange exchange) {
        return simple.createExpression("${file:name.ext.single}")
                .evaluate(exchange, String.class);
    }
}
