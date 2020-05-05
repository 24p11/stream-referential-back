package fr.aphp.referential.load.message.cim10.f001;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import fr.aphp.referential.load.message.Message;

@CsvRecord(separator = "\\|", allowEmptyStream = true)
public final class Cim10Message extends Message {
    @DataField(pos = 1, trim = true)
    private String domainId;

    @DataField(pos = 2, trim = true)
    private String mcoHad;

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

    public String getMcoHad() {
        return mcoHad;
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