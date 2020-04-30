package fr.aphp.referential.load.message;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@CsvRecord(separator = "\\|")
public class Cim10202004Message {
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
}
