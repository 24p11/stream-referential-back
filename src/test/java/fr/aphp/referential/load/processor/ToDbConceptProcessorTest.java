package fr.aphp.referential.load.processor;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.camel.Message;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultMessage;
import org.junit.Assert;
import org.junit.Test;

import fr.aphp.referential.load.bean.ConceptBean;
import fr.aphp.referential.load.bean.UpdateConceptBean;

import static fr.aphp.referential.load.domain.type.SourceType.CIM10;
import static fr.aphp.referential.load.util.CamelUtils.DISABLE_END_DATE;
import static fr.aphp.referential.load.util.CamelUtils.UPDATE_CONCEPT_BEAN;
import static fr.aphp.referential.load.util.CamelUtils.VALIDITY_DATE;

public class ToDbConceptProcessorTest {
    @Test
    public void test() {
        // Given
        Message message = message();

        // When
        ToDbConceptProcessor.setUpdateConceptBeanHeader(message);

        // Then
        UpdateConceptBean updateConceptBean = message.getHeader(UPDATE_CONCEPT_BEAN, UpdateConceptBean.class);
        Assert.assertEquals(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), updateConceptBean.endDate());
        Assert.assertEquals(CIM10, updateConceptBean.vocabularyId());
    }

    private static Message message() {
        Message message = new DefaultMessage(new DefaultCamelContext());

        message.setHeader(UPDATE_CONCEPT_BEAN, updateConceptBean(DISABLE_END_DATE));
        message.setHeader(VALIDITY_DATE, new Date());
        message.setBody(conceptBean());

        return message;
    }

    private static UpdateConceptBean updateConceptBean(String endDate) {
        return UpdateConceptBean.of(CIM10, endDate);
    }

    private static ConceptBean conceptBean() {
        return ConceptBean.builder()
                .vocabularyId(CIM10)
                .conceptCode("AAFF00")
                .conceptName("Label")
                .startDate(new Date())
                .build();
    }
}