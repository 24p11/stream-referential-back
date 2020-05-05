package fr.aphp.referential.load.processor;

import java.text.SimpleDateFormat;

import org.apache.camel.Message;

import fr.aphp.referential.load.bean.ConceptBean;
import fr.aphp.referential.load.bean.UpdateConceptBean;

import static fr.aphp.referential.load.util.CamelUtils.UPDATE_CONCEPT_BEAN;

public class ToDbConceptProcessor {
    public static void setHeaders(Message message) {
        UpdateConceptBean updateConceptBean = message.getHeader(UPDATE_CONCEPT_BEAN, UpdateConceptBean.class);
        ConceptBean referentialBean = message.getBody(ConceptBean.class);
        String startDate = new SimpleDateFormat("yyyy-MM-dd").format(referentialBean.startDate());

        message.setHeader(UPDATE_CONCEPT_BEAN, UpdateConceptBean.of(updateConceptBean.vocabularyId(), startDate));
    }
}
