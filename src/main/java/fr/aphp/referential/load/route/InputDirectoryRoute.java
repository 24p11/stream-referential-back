package fr.aphp.referential.load.route;

import org.apache.camel.builder.EndpointConsumerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import fr.aphp.referential.load.configuration.ApplicationConfiguration;

import static fr.aphp.referential.load.util.CamelUtils.INPUT_DIRECTORY_ROUTE_ID;

@Component
public class InputDirectoryRoute extends BaseRoute {
    private static final Logger LOGGER = LoggerFactory.getLogger(InputDirectoryRoute.class);

    private final ApplicationConfiguration applicationConfiguration;

    InputDirectoryRoute(ApplicationConfiguration applicationConfiguration) {
        this.applicationConfiguration = applicationConfiguration;

        setOutput(log("demo"));
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        applicationConfiguration.getInputDirectories().forEach(this::buildRoute);
    }

    private void buildRoute(String directory) {
        from(fileEndpoint(directory))
                .routeId(directoryRouteId(INPUT_DIRECTORY_ROUTE_ID, directory))
                .threads(applicationConfiguration.getBatchSize())

                .to(getOutput());
    }

    private EndpointConsumerBuilder fileEndpoint(String directory) {
        return file(directory)
                .delay(applicationConfiguration.getPollDelay())
                .advanced()
                .startingDirectoryMustHaveAccess(true);
    }
}