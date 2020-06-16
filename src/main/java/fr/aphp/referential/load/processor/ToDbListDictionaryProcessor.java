package fr.aphp.referential.load.processor;

import java.util.Map;
import java.util.stream.Stream;

import fr.aphp.referential.load.bean.ListDictionaryBean;
import fr.aphp.referential.load.bean.MetadataBean;
import fr.aphp.referential.load.message.MetadataMessage;

import static fr.aphp.referential.load.domain.type.list.f001.ListMetadataType.AUTHOR;
import static fr.aphp.referential.load.domain.type.list.f001.ListMetadataType.VERSION;

public class ToDbListDictionaryProcessor {
    public static Stream<ListDictionaryBean> listDictionaryBeanStream(MetadataMessage metadataMessage) {
        MetadataBean metadataBean = metadataMessage.metadataBean();
        Map<String, Object> any = metadataMessage.metadataContentBean().any();
        return any.keySet().stream()
                .filter(ToDbListDictionaryProcessor::filter)
                .map(key -> ListDictionaryBean.builder()
                        .vocabularyId(metadataBean.vocabularyId())
                        .name(key)
                        .version(any.get(VERSION.representation()).toString())
                        .author(any.get(AUTHOR.representation()).toString())
                        .startDate(metadataBean.startDate())
                        .build());
    }

    private static boolean filter(String key) {
        return !(key.equals(AUTHOR.representation()) || key.equals(VERSION.representation()));
    }
}
