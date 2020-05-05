package fr.aphp.referential.load.message.ccam.f002;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@CsvRecord(separator = "\\|", allowEmptyStream = true)
public final class CcamMessage {
    @DataField(pos = 1, trim = true, length = 7)
    private String domainId;

    @DataField(pos = 2, trim = true)
    private int phase;

    @DataField(pos = 3, trim = true)
    private int activity;

    @DataField(pos = 4, trim = true)
    private int extension;

    @DataField(pos = 5, trim = true)
    private String label;

    public String getDomainId() {
        return domainId;
    }

    public int getPhase() {
        return phase;
    }

    public int getActivity() {
        return activity;
    }

    public int getExtension() {
        return extension;
    }

    public String getLabel() {
        return label;
    }
}
