package fr.aphp.referential.load.configuration;

import java.util.Collection;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "application")
public class ApplicationConfiguration {
    private String pollDelay = "2s";
    private int batchSize = 4;
    private Collection<String> inputDirectories;
    private String failureDirectory;

    public String getPollDelay() {
        return pollDelay;
    }

    public void setPollDelay(String pollDelay) {
        this.pollDelay = pollDelay;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public Collection<String> getInputDirectories() {
        return inputDirectories;
    }

    public void setInputDirectories(Collection<String> inputDirectories) {
        this.inputDirectories = inputDirectories;
    }

    public String getFailureDirectory() {
        return failureDirectory;
    }

    public void setFailureDirectory(String failureDirectory) {
        this.failureDirectory = failureDirectory;
    }
}
