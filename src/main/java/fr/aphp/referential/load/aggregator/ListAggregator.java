package fr.aphp.referential.load.aggregator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.apache.commons.lang3.StringUtils;

public class ListAggregator implements AggregationStrategy {
    @Override
    @SuppressWarnings("unchecked")
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if (null == oldExchange) {
            List<Object> bodyList = new ArrayList<>();

            newExchange.getIn().getBody(Optional.class).ifPresent(bodyList::add);

            newExchange.getIn().setBody(bodyList);

            return newExchange;
        }

        List<Object> bodyList = oldExchange.getIn().getBody(List.class);

        newExchange.getIn().getBody(Optional.class).ifPresent(bodyList::add);

        newExchange.getIn().setBody(bodyList);

        return newExchange;
    }

    // TODO rm this ?
    private Optional<Object> optionalObject(Object object) {
        if (object instanceof String) {
            return StringUtils.isNotBlank(object.toString())
                    ? Optional.of(object)
                    : Optional.empty();
        } else {
            return Optional.ofNullable(object);
        }
    }
}
