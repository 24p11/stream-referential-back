package fr.aphp.referential.load.processor.list.f002;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.apache.camel.Exchange;

import fr.aphp.referential.load.domain.type.SourceType;
import io.vavr.control.Try;

import static fr.aphp.referential.load.domain.type.list.f002.ListMetadataType.AUTHOR;
import static fr.aphp.referential.load.domain.type.list.f002.ListMetadataType.END_DATE;
import static fr.aphp.referential.load.domain.type.list.f002.ListMetadataType.NAME;
import static fr.aphp.referential.load.domain.type.list.f002.ListMetadataType.START_DATE;
import static fr.aphp.referential.load.domain.type.list.f002.ListMetadataType.VERSION;
import static fr.aphp.referential.load.domain.type.list.f002.ListMetadataType.VOCABULARY;

public class ListProcessor {
    public static final String SEPARATOR = ",";

    public static void setHeaders(Exchange exchange) {
        var message = exchange.getIn();
        var body = message.getBody(String.class);

        var metadata = body.split("\n");
        message.setHeader(NAME.name(), metadata[0]);
        message.setHeader(VOCABULARY.name(), SourceType.fromRepresentation(metadata[1]));
        message.setHeader(VERSION.name(), metadata[2]);
        message.setHeader(AUTHOR.name(), metadata[3]);

        var dates = metadata[4].split(SEPARATOR);
        message.setHeader(START_DATE.name(), toDate(dates[0]));
        message.setHeader(END_DATE.name(), Optional.ofNullable(dates[1]).map(ListProcessor::toDate));
    }

    private static Date toDate(String date) {
        String dateFormat = "ddMMyyyy";
        return Try.of(() -> new SimpleDateFormat(dateFormat).parse(date.trim()))
                .getOrElseThrow(() -> new RuntimeException(String.format("Invalid date format '%s' must be '%s'", date, dateFormat)));
    }
}
