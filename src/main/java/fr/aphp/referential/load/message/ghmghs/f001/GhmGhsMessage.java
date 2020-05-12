package fr.aphp.referential.load.message.ghmghs.f001;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@CsvRecord(separator = ";", allowEmptyStream = true, quotingEscaped = true)
public class GhmGhsMessage {
    @DataField(pos = 1, trim = true)
    private String ghs;

    @DataField(pos = 2, trim = true)
    private String cmd;

    @DataField(pos = 3, trim = true)
    private String dcs;

    @DataField(pos = 4, trim = true)
    private String ghm;

    @DataField(pos = 5, trim = true)
    private String ghmLabel;

    @DataField(pos = 6, trim = true)
    private String seuLow;

    @DataField(pos = 7, trim = true)
    private String seuUp;

    @DataField(pos = 8, trim = true)
    private String ghsPrice;

    @DataField(pos = 9, trim = true)
    private String exbPackage;

    @DataField(pos = 10, trim = true)
    private String exbDaily;

    @DataField(pos = 11, trim = true)
    private String exhPrice;

    @DataField(pos = 12, trim = true)
    private String startDate;
}
