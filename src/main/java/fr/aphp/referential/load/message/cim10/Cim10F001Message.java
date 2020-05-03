package fr.aphp.referential.load.message.cim10;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import fr.aphp.referential.load.message.Message;

@CsvRecord(separator = "\\|")
public final class Cim10F001Message extends Message {
    @DataField(pos = 1, trim = true)
    private String domainId;

    @DataField(pos = 2, trim = true)
    private String typeMcoHad;

    @DataField(pos = 3, trim = true)
    private String ssr;

    @DataField(pos = 4, trim = true)
    private String psy;

    @DataField(pos = 5, trim = true)
    private String shortLabel;

    @DataField(pos = 6, trim = true)
    private String label;

    public String getDomainId() {
        return domainId;
    }

    public String getTypeMcoHad() {
        return typeMcoHad;
    }

    public String getSsr() {
        return ssr;
    }

    public String getPsy() {
        return psy;
    }

    public String getShortLabel() {
        return shortLabel;
    }

    public String getLabel() {
        return label;
    }
}
