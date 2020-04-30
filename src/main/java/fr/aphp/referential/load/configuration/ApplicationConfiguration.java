package fr.aphp.referential.load.configuration;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import fr.aphp.referential.load.domain.type.SourceType;

@Configuration
@ConfigurationProperties(prefix = "application")
public class ApplicationConfiguration {
    private int pollDelaySecond = 2;
    private int batchSize = 1000;
    private Map<SourceType, String> inputDirectories;
    private String successDirectory;
    private String failureDirectory;

    public int getPollDelaySecond() {
        return pollDelaySecond;
    }

    public void setPollDelaySecond(int pollDelaySecond) {
        this.pollDelaySecond = pollDelaySecond;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public Map<SourceType, String> getInputDirectories() {
        return inputDirectories;
    }

    public void setInputDirectories(Map<SourceType, String> inputDirectories) {
        this.inputDirectories = inputDirectories;
    }

    public String getSuccessDirectory() {
        return successDirectory;
    }

    public void setSuccessDirectory(String successDirectory) {
        this.successDirectory = successDirectory;
    }

    public String getFailureDirectory() {
        return failureDirectory;
    }

    public void setFailureDirectory(String failureDirectory) {
        this.failureDirectory = failureDirectory;
    }
}
