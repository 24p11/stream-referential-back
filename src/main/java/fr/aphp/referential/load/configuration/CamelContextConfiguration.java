package fr.aphp.referential.load.configuration;

import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;

public class CamelContextConfiguration {

    @Autowired
    public CamelContextConfiguration(CamelContext camelContext) {
        camelContext.setAllowUseOriginalMessage(true);
    }
}
