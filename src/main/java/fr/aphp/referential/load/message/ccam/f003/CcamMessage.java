package fr.aphp.referential.load.message.ccam.f003;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@CsvRecord(separator = "\\|", allowEmptyStream = true)
public final class CcamMessage {
    @DataField(pos = 1, trim = true, length = 7)
    private String conceptCode;

    @DataField(pos = 2, trim = true)
    private String extensionCodes;

    public String getConceptCode() {
        return conceptCode;
    }

    public String getExtensionCodes() {
        return extensionCodes;
    }
}
