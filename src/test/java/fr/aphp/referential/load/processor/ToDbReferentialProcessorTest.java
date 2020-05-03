package fr.aphp.referential.load.processor;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.camel.Message;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.support.DefaultMessage;
import org.junit.Assert;
import org.junit.Test;

import fr.aphp.referential.load.bean.ReferentialBean;
import fr.aphp.referential.load.bean.UpdateReferentialBean;

import static fr.aphp.referential.load.domain.type.SourceType.CIM10;
import static fr.aphp.referential.load.util.CamelUtils.DISABLE_END_DATE;
import static fr.aphp.referential.load.util.CamelUtils.UPDATE_REFERENTIAL_BEAN;

public class ToDbReferentialProcessorTest {
    @Test
    public void test() {
        // Given
        Message message = message();

        // When
        ToDbReferentialProcessor.setHeaders(message);

        // Then
        UpdateReferentialBean updateReferentialBean = message.getHeader(UPDATE_REFERENTIAL_BEAN, UpdateReferentialBean.class);
        Assert.assertEquals(new SimpleDateFormat("yyyy-MM-dd").format(new Date()), updateReferentialBean.endDate());
        Assert.assertEquals(CIM10, updateReferentialBean.sourceType());
    }

    private static Message message() {
        Message message = new DefaultMessage(new DefaultCamelContext());

        message.setHeader(UPDATE_REFERENTIAL_BEAN, updateReferentialBean(DISABLE_END_DATE));
        message.setBody(referentialBean());

        return message;
    }

    private static UpdateReferentialBean updateReferentialBean(String endDate) {
        return UpdateReferentialBean.of(CIM10, endDate);
    }

    private static ReferentialBean referentialBean() {
        return ReferentialBean.builder()
                .type(CIM10)
                .domainId("AAFF00")
                .label("Label")
                .startDate(new Date())
                .build();
    }
}