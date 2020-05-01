package fr.aphp.referential.load.lifecycle;

import org.apache.camel.spring.SpringCamelContext;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Configuration;

import io.vavr.control.Try;

@Configuration
public class SpringBootCamelManageLifecycle implements SmartLifecycle {
    private final SpringCamelContext springCamelContext;

    public SpringBootCamelManageLifecycle(SpringCamelContext springCamelContext) {
        this.springCamelContext = springCamelContext;
    }

    @Override
    public void start() {}

    @Override
    public void stop() {
        // Wait for camelContext to stop before sending stop signal
        while (springCamelContext.isStarted()) {
            Try.run(() -> Thread.sleep(1000));
        }
    }

    @Override
    public boolean isRunning() {
        return springCamelContext.isStarted();
    }
}
