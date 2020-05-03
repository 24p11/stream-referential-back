package fr.aphp.referential.load.processor;

import java.text.SimpleDateFormat;

import org.apache.camel.Message;

import fr.aphp.referential.load.bean.ReferentialBean;
import fr.aphp.referential.load.bean.UpdateReferentialBean;

import static fr.aphp.referential.load.util.CamelUtils.UPDATE_REFERENTIAL_BEAN;

public class ToDbReferentialProcessor {
    public static void setHeaders(Message message) {
        UpdateReferentialBean updateReferentialBean = message.getHeader(UPDATE_REFERENTIAL_BEAN, UpdateReferentialBean.class);
        ReferentialBean referentialBean = message.getBody(ReferentialBean.class);
        String startDate = new SimpleDateFormat("yyyy-MM-dd").format(referentialBean.startDate());

        message.setHeader(UPDATE_REFERENTIAL_BEAN, UpdateReferentialBean.of(updateReferentialBean.sourceType(), startDate));
    }
}
