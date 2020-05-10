package fr.aphp.referential.load.message.cim10.f001;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import fr.aphp.referential.load.message.Message;

@CsvRecord(separator = "\\|", allowEmptyStream = true)
public final class Cim10Message implements Message {
    @DataField(pos = 1, trim = true)
    private String conceptCode;

    @DataField(pos = 2, trim = true)
    private String mcoHad;

    @DataField(pos = 3, trim = true)
    private String ssr;

    @DataField(pos = 4, trim = true)
    private String psy;

    @DataField(pos = 5, trim = true)
    private String shortConceptName;

    @DataField(pos = 6, trim = true)
    private String conceptName;

    public String getConceptCode() {
        return conceptCode;
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

    public String getShortConceptName() {
        return shortConceptName;
    }

    public String getConceptName() {
        return conceptName;
    }
}
