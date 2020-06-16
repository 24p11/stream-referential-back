package fr.aphp.referential.load.route;

import org.springframework.stereotype.Component;

import fr.aphp.referential.load.message.MetadataMessage;
import fr.aphp.referential.load.processor.ToDbListDictionaryProcessor;

import static fr.aphp.referential.load.util.CamelUtils.TO_DB_LIST_DICTIONARY_ROUTE_ID;

@Component
public class ToDbListDictionaryRoute extends BaseRoute {
    public ToDbListDictionaryRoute() {
        setInput(direct(TO_DB_LIST_DICTIONARY_ROUTE_ID));
    }

    @Override
    public void configure() throws Exception {
        mysqlDeadlockExceptionHandler();

        super.configure();

        from(getInput())
                .routeId(TO_DB_LIST_DICTIONARY_ROUTE_ID)

                .log("Updating list dictionary")

                .split(body()).parallelProcessing()

                .transform().body(MetadataMessage.class, ToDbListDictionaryProcessor::listDictionaryBeanStream)

                .split(body())

                .to(mybatisBatchInsert("insertIgnoreListDictionary"));
    }
}
