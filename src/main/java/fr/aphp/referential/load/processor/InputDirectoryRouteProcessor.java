package fr.aphp.referential.load.processor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.spi.Language;
import org.apache.commons.io.FilenameUtils;

import io.vavr.control.Try;

import static fr.aphp.referential.load.util.CamelUtils.FILE_EXT_SEPARATOR;
import static fr.aphp.referential.load.util.CamelUtils.FORMAT;
import static fr.aphp.referential.load.util.CamelUtils.VALIDITY_DATE;
import static org.apache.camel.Exchange.FILE_NAME;

public class InputDirectoryRouteProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws RuntimeException {
        checkExtension()
                .andThen(setHeaders())
                .accept(exchange);
    }

    private static Consumer<Exchange> checkExtension() {
        return exchange -> {
            Language simple = exchange.getContext().resolveLanguage("simple");

            if (hasNoValidExtension(simple, exchange)) {
                throw new RuntimeException(String.format("Invalid file extension '%s'", fileExtension(simple, exchange)));
            }
        };
    }

    private static Consumer<Exchange> setHeaders() {
        return exchange -> {
            Message message = exchange.getIn();
            String extension = FilenameUtils.getExtension(message.getHeader(FILE_NAME, String.class));
            // FORMAT_DATE_VERSION
            String[] extensionInformation = extension.split(FILE_EXT_SEPARATOR);
            message.setHeader(FORMAT, extensionInformation[0].toUpperCase());
            message.setHeader(VALIDITY_DATE, validityDate(extensionInformation[1]));
        };
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

    private static Date validityDate(String extensionDate) {
        String dateFormat = "yyyyMMdd";
        return Try.of(() -> new SimpleDateFormat(dateFormat).parse(extensionDate))
                .getOrElseThrow(() -> new RuntimeException(String.format("Invalid date format '%s' must be '%s'", extensionDate, dateFormat)));
    }
}
