package fr.aphp.referential.load.processor;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.camel.Message;

import fr.aphp.referential.load.bean.UpdateConceptBean;

import static fr.aphp.referential.load.util.CamelUtils.UPDATE_CONCEPT_BEAN;
import static fr.aphp.referential.load.util.CamelUtils.VALIDITY_DATE;

public class ToDbConceptProcessor {
    public static void setUpdateConceptBeanHeader(Message message) {
        UpdateConceptBean updateConceptBean = message.getHeader(UPDATE_CONCEPT_BEAN, UpdateConceptBean.class);
        String startDate = new SimpleDateFormat("yyyy-MM-dd").format(message.getHeader(VALIDITY_DATE, Date.class));

        message.setHeader(UPDATE_CONCEPT_BEAN, UpdateConceptBean.of(updateConceptBean.vocabularyId(), startDate));
    }
}
