package fr.aphp.referential.load.aggregator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

public class ListAggregator implements AggregationStrategy {
    @Override
    @SuppressWarnings("unchecked")
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        List<Object> bodyList;
        if (null == oldExchange) {
            bodyList = new ArrayList<>();
        } else {
            bodyList = oldExchange.getIn().getBody(List.class);
        }

        newExchange.getIn().getBody(Optional.class).ifPresent(bodyList::add);
        newExchange.getIn().setBody(bodyList);

        return newExchange;
    }
}
