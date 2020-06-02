package fr.aphp.referential.load.route.list.f001;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.RoutesBuilder;
import org.junit.Test;

import fr.aphp.referential.load.message.list.f001.ListMessage;
import fr.aphp.referential.load.route.BaseRouteTest;

import static fr.aphp.referential.load.domain.type.list.f001.ListMetadataType.AUTHOR;
import static fr.aphp.referential.load.domain.type.list.f001.ListMetadataType.END_DATE;
import static fr.aphp.referential.load.domain.type.list.f001.ListMetadataType.NAME;
import static fr.aphp.referential.load.domain.type.list.f001.ListMetadataType.START_DATE;
import static fr.aphp.referential.load.domain.type.list.f001.ListMetadataType.VERSION;
import static fr.aphp.referential.load.domain.type.list.f001.ListMetadataType.VOCABULARY;

public class ListRouteTest extends BaseRouteTest {
    @Override
    protected RoutesBuilder createRouteBuilder() throws Exception {
        String fileEndpoint = resourceIn("list") + "?noop=true&include=list.csv.F001_20200601";
        return new ListRoute()
                .setInput(fileEndpoint)
                .setOutput(OUT);
    }

    @Test
    public void test() throws InterruptedException, ParseException {
        // Expect
        out.expectedMessageCount(2);
        out.expectedHeaderReceived(NAME.name(), "my_list");
        out.expectedHeaderReceived(VOCABULARY.name(), "dmi");
        out.expectedHeaderReceived(VERSION.name(), "v1");
        out.expectedHeaderReceived(AUTHOR.name(), "John Doe");
        out.expectedHeaderReceived(START_DATE.name(), new SimpleDateFormat("yyyy-MM-dd").parse("2020-12-01"));
        out.expectedHeaderReceived(END_DATE.name(), new SimpleDateFormat("yyyy-MM-dd").parse("2021-01-01"));

        // Then
        assertMockEndpointsSatisfied();

        out.getExchanges().stream()
                .map(Exchange::getIn)
                .map(Message::getBody)
                .forEach(body -> assertIsInstanceOf(ListMessage.class, body));
    }
}