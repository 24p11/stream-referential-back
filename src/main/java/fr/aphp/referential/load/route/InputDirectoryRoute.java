package fr.aphp.referential.load.route;

import java.util.concurrent.TimeUnit;

import org.apache.camel.builder.EndpointConsumerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import fr.aphp.referential.load.bean.UpdateConceptBean;
import fr.aphp.referential.load.configuration.ApplicationConfiguration;
import fr.aphp.referential.load.domain.type.SourceType;
import fr.aphp.referential.load.processor.InputDirectoryRouteProcessor;

import static fr.aphp.referential.load.util.CamelUtils.INPUT_DIRECTORY_ROUTE_ID;
import static fr.aphp.referential.load.util.CamelUtils.SOURCE_TYPE;
import static fr.aphp.referential.load.util.CamelUtils.UPDATE_CONCEPT_BEAN;

@Component
public class InputDirectoryRoute extends BaseRoute {
    private static final Logger LOGGER = LoggerFactory.getLogger(InputDirectoryRoute.class);

    private final ApplicationConfiguration applicationConfiguration;

    InputDirectoryRoute(ApplicationConfiguration applicationConfiguration) {
        this.applicationConfiguration = applicationConfiguration;
    }

    @Override
    public void configure() throws Exception {
        super.configure();

        applicationConfiguration.getInputDirectories().forEach(this::buildRoute);
    }

    private void buildRoute(SourceType sourceType, String directory) {
        from(fileEndpoint(directory))
                .routeId(directoryRouteId(INPUT_DIRECTORY_ROUTE_ID, directory))
                .threads(applicationConfiguration.getPoolSize())

                // Will throw exception if file has no valid extension
                .process(new InputDirectoryRouteProcessor())

                .log("Start processing '${header.CamelFileName}'")

                .setHeader(SOURCE_TYPE, constant(sourceType))
                .setHeader(UPDATE_CONCEPT_BEAN, constant(UpdateConceptBean.of(sourceType)))

                // Disable old entries
                .to(mybatisUpdateEndDate())

                // Process type (CIM10, CCAM...)
                .to(direct(sourceType));
    }

    private EndpointConsumerBuilder fileEndpoint(String directory) {
        return file(directory)
                // TODO remove this when upgrade camel to 3.3.0 https://issues.apache.org/jira/browse/CAMEL-14982
                .initialDelay(0)
                .delay(applicationConfiguration.getPollDelaySecond())
                .timeUnit(TimeUnit.SECONDS)
                .move(applicationConfiguration.getSuccessDirectory())
                .moveFailed(applicationConfiguration.getFailureDirectory())
                .readLock("changed")
                .readLockCheckInterval(3000);
    }

    private static String mybatisUpdateEndDate() {
        return mybatis("updateEndDateConceptBeforeLoad", "UpdateList", "inputHeader=" + UPDATE_CONCEPT_BEAN);
    }
}