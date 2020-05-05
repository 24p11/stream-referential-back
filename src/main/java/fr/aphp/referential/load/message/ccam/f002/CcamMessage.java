package fr.aphp.referential.load.message.ccam.f002;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@CsvRecord(separator = "\\|", allowEmptyStream = true)
public final class CcamMessage {
    @DataField(pos = 1, trim = true, length = 7)
    private String conceptCode;

    @DataField(pos = 2, trim = true)
    private int phase;

    @DataField(pos = 3, trim = true)
    private int activity;

    @DataField(pos = 4, trim = true)
    private String extension;

    @DataField(pos = 5, trim = true)
    private String conceptName;

    public String getConceptCode() {
        return conceptCode;
    }

    public int getPhase() {
        return phase;
    }

    public int getActivity() {
        return activity;
    }

    public String getExtension() {
        return extension;
    }

    public String getConceptName() {
        return conceptName;
    }
}
