package fr.aphp.referential.load.configuration;

import org.apache.camel.CamelContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelContextConfiguration {

    @Autowired
    public CamelContextConfiguration(CamelContext camelContext) {
        camelContext.setAllowUseOriginalMessage(true);
    }
}
