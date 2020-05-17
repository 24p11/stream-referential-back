package fr.aphp.referential.load.route.ghmghs.f001;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.camel.Exchange;
import org.apache.camel.RoutesBuilder;
import org.junit.Test;

import fr.aphp.referential.load.bean.MetadataBean;
import fr.aphp.referential.load.domain.type.SourceType;
import fr.aphp.referential.load.message.MetadataMessage;
import fr.aphp.referential.load.processor.ghmghs.f001.GhmGhsMetadataProcessor;
import fr.aphp.referential.load.route.BaseRoute;
import fr.aphp.referential.load.route.BaseRouteTest;

import static fr.aphp.referential.load.domain.type.SourceType.GHM;
import static fr.aphp.referential.load.domain.type.SourceType.GHS;
import static java.util.stream.Collectors.groupingBy;

public class GhmGhsMetadataRouteTest extends BaseRouteTest {
    @Override
    protected RoutesBuilder[] createRouteBuilders() throws Exception {
        String fileEndpoint = resourceIn("ghmghs") + "?noop=true&include=ghs_pub.csv.F001_20200530";
        BaseRoute ghmGhsRoute = new GhmGhsRoute()
                .setInput(fileEndpoint)
                .setOutput(IN);

        BaseRoute ghmGhsMetadataRoute = new GhmGhsMetadataRoute(new GhmGhsMetadataProcessor())
                .setInput(IN)
                .setOutput(OUT);

        return new RoutesBuilder[]{
                ghmGhsRoute,
                ghmGhsMetadataRoute
        };
    }

    @Test
    public void test() throws InterruptedException {
        // Expected
        out.expectedMinimumMessageCount(3);

        // Then
        assertMockEndpointsSatisfied();

        //noinspection unchecked
        Map<SourceType, List<MetadataBean>> metaDataBeanBySourceTypeMap = (Map<SourceType, List<MetadataBean>>) out.getExchanges().stream()
                .map(Exchange::getIn)
                .flatMap(message -> message.getBody(Stream.class))
                .map(this::metadataBean)
                .peek(this::asserts)
                .collect(groupingBy(MetadataBean::vocabularyId));

        assertEquals(4, metaDataBeanBySourceTypeMap.get(GHM).size());
        assertEquals(8, metaDataBeanBySourceTypeMap.get(GHS).size());
    }

    private <T> MetadataBean metadataBean(T metadataMessage) {
        return ((MetadataMessage) metadataMessage).metadataBean();
    }

    private void asserts(Object body) {
        MetadataBean metadataBean = assertIsInstanceOf(MetadataBean.class, body);
        assertEquals(1, metadataBean.standardConcept());
    }
}