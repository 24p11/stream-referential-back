package fr.aphp.referential.load.message.ccam.f002;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import fr.aphp.referential.load.message.Message;

@CsvRecord(separator = "\\|", allowEmptyStream = true)
public final class CcamMessage implements Message {
    @DataField(pos = 1, trim = true, length = 7)
    private String conceptCode;

    @DataField(pos = 2, trim = true)
    private String phase;

    @DataField(pos = 3, trim = true)
    private String activity;

    @DataField(pos = 4, trim = true)
    private String extension;

    @DataField(pos = 5, trim = true)
    private String conceptName;

    public String getConceptCode() {
        return conceptCode;
    }

    public String getPhase() {
        return phase;
    }

    public String getActivity() {
        return activity;
    }

    public String getExtension() {
        return extension;
    }

    public String getConceptName() {
        return conceptName;
    }
}
